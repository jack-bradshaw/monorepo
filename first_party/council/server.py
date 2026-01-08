import asyncio
import json
import logging
import os
from typing import Dict, List, Set

from fastapi import FastAPI, WebSocket, WebSocketDisconnect
from fastapi.staticfiles import StaticFiles
from fastapi.responses import FileResponse
from pydantic import BaseModel
import google.generativeai as genai

# Configure logging
logging.basicConfig(level=logging.INFO)
logger = logging.getLogger("server")

app = FastAPI()

# Configure API Key
API_KEY = os.environ.get("GOOGLE_API_KEY")
if not API_KEY:
    logger.warning(
        "GOOGLE_API_KEY environment variable is not set. Waiting for UI configuration."
    )
else:
    genai.configure(api_key=API_KEY)


class ConfigRequest(BaseModel):
    api_key: str


@app.get("/config/status")
async def get_config_status():
    global API_KEY
    return {"configured": bool(API_KEY)}


@app.post("/config/setup")
async def setup_config(config: ConfigRequest):
    global API_KEY
    API_KEY = config.api_key
    genai.configure(api_key=API_KEY)
    logger.info("API Key configured via UI.")

    # Broadcast to simulation (if connected) or all clients
    if hasattr(app, "manager"):
        await app.manager.broadcast_to_ui({"type": "config_update", "api_key": API_KEY})
    return {"status": "success"}


# Serve static files for the UI
BASE_DIR = os.path.dirname(os.path.abspath(__file__))
app.mount(
    "/static", StaticFiles(directory=os.path.join(BASE_DIR, "static")), name="static"
)


class ConnectionManager:
    def __init__(self):
        # Active connections: client_id -> WebSocket
        self.active_connections: Dict[str, WebSocket] = {}
        # UI connections (receive all updates)
        self.ui_connections: List[WebSocket] = []
        # Message history for the reporter
        self.history: List[dict] = []
        # Registry of agent details: client_id -> {display_name, role_desc}
        self.registry: Dict[str, dict] = {}
        # Voting state: client_id -> "YES" | "NO"
        self.votes: Dict[str, str] = {}

        # Current Phase
        self.current_phase = "OBJECTIVE"

        # Load config
        try:
            config_path = os.path.join(BASE_DIR, "simulation_config.json")
            with open(config_path, "r") as f:
                config = json.load(f)
                active_agents = config.get("active_agents")
                self.expected_agent_count = len(active_agents) if active_agents else 21

                # Load Thresholds
                self.thresholds = config.get(
                    "vote_thresholds", {"phase_change": 0.75, "intervention": 0.5}
                )
        except Exception:
            self.expected_agent_count = 21
            self.thresholds = {"phase_change": 0.75, "intervention": 0.5}

        self.is_running = True

    async def connect(self, websocket: WebSocket, client_id: str):
        await websocket.accept()
        if client_id == "ui":
            self.ui_connections.append(websocket)
            logger.info("UI connected")
            # Send config to UI
            await websocket.send_json(
                {
                    "type": "config",
                    "expected_agent_count": self.expected_agent_count,
                    "current_phase": self.current_phase,
                }
            )
            # Send existing registrations
            for agent_id, details in self.registry.items():
                await websocket.send_json(
                    {
                        "type": "register",
                        "sender": agent_id,
                        "display_name": details["display_name"],
                        "role_desc": details["role_desc"],
                        "model": details.get(
                            "model", "gemini-2.5-flash-preview-09-2025"
                        ),
                    }
                )
        else:
            self.active_connections[client_id] = websocket
            logger.info(f"Agent connected: {client_id}")

    def disconnect(self, websocket: WebSocket, client_id: str):
        if client_id == "ui":
            if websocket in self.ui_connections:
                self.ui_connections.remove(websocket)
            logger.info("UI disconnected")
        else:
            if client_id in self.active_connections:
                del self.active_connections[client_id]
            logger.info(f"Agent disconnected: {client_id}")

    async def broadcast_to_ui(self, message: dict):
        """Send a message to all connected UI clients."""
        for connection in self.ui_connections:
            try:
                await connection.send_json(message)
            except Exception as e:
                logger.error(f"Error broadcasting to UI: {e}")

    async def send_personal_message(self, message: dict, recipient_id: str):
        """Send a message to a specific agent."""
        if recipient_id in self.active_connections:
            try:
                await self.active_connections[recipient_id].send_json(message)
            except Exception as e:
                logger.error(f"Error sending to {recipient_id}: {e}")

    async def broadcast(self, message: dict, sender_id: str):
        """Send a message to all agents (except sender) and UI."""
        # Save to history (only actual messages, not system updates or thoughts if we want a clean report)
        if message.get("type") == "message":
            self.history.append(message)

        # Send to UI
        await self.broadcast_to_ui(message)

        # Send to all other agents
        for client_id, connection in self.active_connections.items():
            if client_id != sender_id:
                try:
                    await connection.send_json(message)
                except Exception as e:
                    logger.error(f"Error broadcasting to {client_id}: {e}")

    async def broadcast_system_message(self, content: str):
        """Helper to broadcast system messages to UI."""
        await self.broadcast_to_ui(
            {"type": "system", "content": content, "sender": "system"}
        )

    async def broadcast_signal(self, signal_type: str):
        """Helper to broadcast control signals (pause, resume, stop) to all agents."""
        for client_id, connection in list(self.active_connections.items()):
            try:
                await connection.send_json({"type": signal_type})
            except:
                pass


manager = ConnectionManager()
app.manager = manager  # Attach manager to app for access in routes


@app.get("/")
async def get():
    return FileResponse(os.path.join(BASE_DIR, "static/index.html"))


@app.post("/pause")
async def pause_simulation():
    # Broadcast pause signal
    await manager.broadcast_system_message("Simulation PAUSED.")
    await manager.broadcast_signal("pause")
    return {"status": "paused"}


@app.post("/start")
async def start_simulation():
    # Broadcast start signal (same as resume for agents, but different UI message)
    await manager.broadcast_to_ui(
        {"type": "system", "content": "Simulation STARTED.", "sender": "system"}
    )

    # Broadcast Instructions
    try:
        # Load Main Instructions
        with open(
            os.path.join(BASE_DIR, "instructions/main_instructions.txt"), "r"
        ) as f:
            main_instructions = f.read()

        config_path = os.path.join(BASE_DIR, "simulation_config.json")
        with open(config_path, "r") as f:
            config = json.load(f)
            scenario_file = config.get(
                "scenario_file", "instructions/ai_infrastructure.txt"
            )
            instruction_path = os.path.join(BASE_DIR, scenario_file)

        with open(instruction_path, "r") as f:
            specific_instructions = f.read()

        instructions = f"{main_instructions}\n\n{specific_instructions}"

        await manager.broadcast(
            {
                "type": "message",
                "recipient": "all",
                "content": f"SYSTEM INSTRUCTIONS:\n\n{instructions}",
                "sender": "system",
            },
            "system",
        )
    except Exception as e:
        logger.error(f"Error broadcasting instructions: {e}")

    for client_id, connection in list(manager.active_connections.items()):
        try:
            await connection.send_json({"type": "resume"})
        except:
            pass
    return {"status": "started"}


@app.post("/resume")
async def resume_simulation():
    # Broadcast resume signal
    await manager.broadcast_system_message("Simulation RESUMED.")
    await manager.broadcast_signal("resume")
    return {"status": "resumed"}


class Interjection(BaseModel):
    recipient: str
    content: str


@app.post("/interject")
async def interject(interjection: Interjection):
    message = {
        "type": "message",
        "sender": "Engineer",
        "recipient": interjection.recipient,
        "content": f"[ENGINEER INTERJECTION]: {interjection.content}",
        "status": "SPEAKING 🗣️",
        "sentiment": "DIRECTIVE ⚠️",
    }

    if interjection.recipient == "all":
        await manager.broadcast(message, "Engineer")
    else:
        await manager.send_personal_message(message, interjection.recipient)
        await manager.broadcast_to_ui(message)

    return {"status": "sent"}


class GuidanceRequest(BaseModel):
    content: str


@app.post("/submit_guidance")
async def submit_guidance(guidance: GuidanceRequest):
    # 1. Broadcast Guidance
    message = {
        "type": "message",
        "sender": "Engineer",
        "recipient": "all",
        "content": f"[ENGINEER GUIDANCE]: {guidance.content}",
        "status": "SPEAKING 🗣️",
        "sentiment": "DIRECTIVE ⚠️",
    }
    await manager.broadcast(message, "Engineer")

    # 2. Resume Simulation
    for client_id, connection in list(manager.active_connections.items()):
        try:
            await connection.send_json({"type": "resume"})
        except:
            pass

    return {"status": "guidance_sent_and_resumed"}


@app.post("/stop")
async def stop_simulation():
    manager.is_running = False
    # Broadcast stop signal to all agents
    await manager.broadcast_to_ui(
        {"type": "system", "content": "Simulation stopping...", "sender": "system"}
    )
    # Iterate over a copy of items to avoid runtime errors if connections close
    for client_id, connection in list(manager.active_connections.items()):
        try:
            await connection.send_json({"type": "stop"})
        except:
            pass
    return {"status": "stopping"}


@app.post("/shutdown")
async def shutdown(save_history: bool = False):
    if save_history:
        try:
            # Determine scenario name from config
            try:
                config_path = os.path.join(BASE_DIR, "simulation_config.json")
                with open(config_path, "r") as f:
                    config = json.load(f)
                    scenario_file = config.get(
                        "scenario_file", "instructions/ai_infrastructure.txt"
                    )
                    instruction_path = os.path.join(BASE_DIR, scenario_file)
                    # Extract filename without extension
                    scenario_name = os.path.splitext(
                        os.path.basename(instruction_path)
                    )[0]
            except:
                scenario_name = "simulation"

            os.makedirs("artifacts", exist_ok=True)
            filename = f"artifacts/{scenario_name}_history.json"

            with open(filename, "w") as f:
                json.dump(manager.history, f, indent=2)
            logger.info(f"History saved to {filename}")
            return {"status": "shutdown", "saved_file": filename}
        except Exception as e:
            logger.error(f"Error saving history: {e}")
            return {"status": "shutdown", "error": str(e)}

    # In a real app we might want a cleaner shutdown, but for this script:
    # We'll delay exit slightly to let the response go through
    asyncio.create_task(delayed_exit())
    return {"status": "shutdown"}


async def delayed_exit():
    await asyncio.sleep(1)
    os._exit(0)


@app.get("/history")
async def get_history():
    return manager.history


@app.websocket("/ws/{client_id}")
async def websocket_endpoint(websocket: WebSocket, client_id: str):
    await manager.connect(websocket, client_id)
    try:
        while True:
            data = await websocket.receive_text()
            try:
                message_data = json.loads(data)
                # Message structure:
                # {
                #   "type": "message" | "thought",
                #   "recipient": "all" | "agent_id",
                #   "content": "...",
                #   "sender": client_id
                # }

                # Ensure sender is set correctly
                message_data["sender"] = client_id

                if message_data.get("type") == "register":
                    # Store registration details
                    display_name = message_data.get("display_name")
                    is_secret = message_data.get("is_secret", False)
                    model = message_data.get(
                        "model", "gemini-2.5-flash-preview-09-2025"
                    )

                    manager.registry[client_id] = {
                        "display_name": display_name,
                        "role_desc": message_data.get("role_desc"),
                        "is_secret": is_secret,
                        "model": model,
                    }
                    await manager.broadcast_to_ui(message_data)

                    # Announce connection with display name (ONLY if not secret)
                    if not is_secret:
                        await manager.broadcast_to_ui(
                            {
                                "type": "system",
                                "content": f"{display_name} has joined the simulation.",
                                "sender": "system",
                            }
                        )

                elif message_data.get("type") == "thought":
                    # Thoughts only go to UI
                    await manager.broadcast_to_ui(message_data)

                elif message_data.get("type") == "message":
                    if message_data.get("recipient") == "all":
                        # Broadcast to everyone
                        await manager.broadcast(message_data, client_id)

                        # Check for Solution Summary
                        if message_data.get("type") == "solution_summary":
                            logger.info(f"Solution Summary received from {client_id}")
                            await manager.broadcast_to_ui(
                                {
                                    "type": "solution_popup",
                                    "content": message_data.get("content"),
                                    "sender": client_id,
                                }
                            )

                    else:
                        # Direct message
                        recipient = message_data.get("recipient")
                        await manager.send_personal_message(message_data, recipient)
                        # Also send to UI for monitoring
                        await manager.broadcast_to_ui(message_data)

                # Track Votes
                vote = message_data.get("vote")
                if vote:
                    vote = vote.upper()
                    manager.votes[client_id] = vote

                    active_count = len(manager.active_connections)

                    # 1. Check Intervention Vote (YES)
                    yes_count = sum(1 for v in manager.votes.values() if v == "YES")
                    intervention_threshold = manager.thresholds.get("intervention", 0.5)
                    if (
                        active_count > 0
                        and (yes_count / active_count) >= intervention_threshold
                    ):
                        logger.info("Vote Threshold Met! Requesting Intervention.")
                        await manager.broadcast_to_ui(
                            {
                                "type": "system",
                                "content": f"⚠️ VOTE PASSED ({yes_count}/{active_count}): Council requests Engineer Intervention.",
                                "sender": "system",
                            }
                        )
                        await manager.broadcast_to_ui(
                            {"type": "user_intervention_needed", "sender": "system"}
                        )
                        for cid, conn in list(manager.active_connections.items()):
                            try:
                                await conn.send_json({"type": "pause"})
                            except:
                                pass
                        manager.votes.clear()

                    # 2. Check Phase Vote (MOVE: <PHASE>)
                    # Group votes by target phase
                    phase_votes = {}
                    for v in manager.votes.values():
                        if v.startswith("MOVE:"):
                            target = v.split(":", 1)[1].strip()
                            phase_votes[target] = phase_votes.get(target, 0) + 1

                    # Check if any phase meets threshold
                    phase_threshold = manager.thresholds.get("phase_change", 0.75)
                    for phase, count in phase_votes.items():
                        if (
                            active_count > 0
                            and (count / active_count) >= phase_threshold
                        ):
                            logger.info(f"Phase Vote Passed: Moving to {phase}")
                            manager.current_phase = phase

                            # Broadcast System Announcement
                            await manager.broadcast(
                                {
                                    "type": "message",
                                    "recipient": "all",
                                    "content": f"📢 PHASE CHANGE: The Council has voted to move to **{phase}**.",
                                    "sender": "system",
                                },
                                "system",
                            )

                            # Update UI
                            await manager.broadcast_to_ui(
                                {
                                    "type": "phase_update",
                                    "phase": phase,
                                    "sender": "system",
                                }
                            )

                            manager.votes.clear()
                            break

            except json.JSONDecodeError:
                pass
            except Exception as e:
                logger.error(f"Error processing message from {client_id}: {e}")

    except WebSocketDisconnect:
        manager.disconnect(websocket, client_id)


if __name__ == "__main__":
    import argparse
    import uvicorn

    parser = argparse.ArgumentParser()
    parser.add_argument(
        "--port", type=int, default=8085, help="Port to run the server on"
    )
    args = parser.parse_args()

    uvicorn.run(app, host="0.0.0.0", port=args.port)
