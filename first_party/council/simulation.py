import asyncio
import json
import logging
import os
import random
import websockets
import google.generativeai as genai
from typing import List
import requests

# Configure logging
logging.basicConfig(
    level=logging.INFO, format="%(asctime)s - %(name)s - %(levelname)s - %(message)s"
)
logger = logging.getLogger("simulation")

import argparse

parser = argparse.ArgumentParser()
parser.add_argument("--port", type=int, default=8085, help="Port to connect to")
args = parser.parse_args()

# Configuration
SERVER_URL = f"ws://localhost:{args.port}/ws"
API_URL = f"http://localhost:{args.port}"
API_KEY = os.environ.get("GOOGLE_API_KEY")
BASE_DIR = os.path.dirname(os.path.abspath(__file__))

if not API_KEY:
    logger.warning(
        "GOOGLE_API_KEY environment variable is not set. Waiting for server configuration."
    )
else:
    genai.configure(api_key=API_KEY)


class Agent:
    def __init__(self, metadata: dict, prompt_file: str):
        self.metadata = metadata  # Store for later access
        self.name = metadata["id"]
        self.display_name = metadata["name"]
        self.role_desc = metadata["role"]
        self.is_secret = metadata.get("is_secret", False)
        self.history = []
        self.status = "IDLING"
        self.sentiment = "NEUTRAL 😐"
        self.running = True
        self.paused = True
        self.websocket = None
        self.total_tokens = 0

        with open(prompt_file, "r") as f:
            base_prompt = f.read()

        # Dynamic Header Injection
        header = f"YOU ARE AGENT {metadata['number']}: {metadata['name'].upper()} ({metadata['role']})\n\n"

        # Load Instructions
        try:
            # Load Main Instructions
            with open(
                os.path.join(BASE_DIR, "instructions/main_instructions.md"), "r"
            ) as f:
                main_instructions = f.read()

            # Load Specific Instructions from Config
            config_path = os.path.join(BASE_DIR, "simulation_config.json")
            with open(config_path, "r") as f:
                config = json.load(f)
                scenario_file = config.get(
                    "scenario_file", "instructions/ai_infrastructure.md"
                )
                instruction_path = os.path.join(BASE_DIR, scenario_file)

            with open(instruction_path, "r") as f:
                specific_instructions = f.read()

            instructions = f"{main_instructions}\n\n{specific_instructions}"
        except Exception as e:
            logger.error(f"Error loading instructions: {e}")
            instructions = ""

        self.system_prompt = header + base_prompt + "\n\n" + instructions

        # Use model from metadata, fallback to Flash
        model_name = metadata.get("model", "gemini-2.5-flash-preview-09-2025")
        logger.info(f"{self.name} using model: {model_name}")
        self.model = genai.GenerativeModel(model_name)
        self.chat = self.model.start_chat(
            history=[{"role": "user", "parts": [self.system_prompt]}]
        )

    async def act_loop(self):
        # Initial delay
        await asyncio.sleep(random.uniform(2, 5))

        last_config_check = 0
        interval = 10

        while self.running:
            if self.paused:
                await asyncio.sleep(1)
                continue

            # Decide whether to act
            if random.random() < 0.3:  # 30% chance to act
                await self.think_and_act()

            # Update status to IDLING if not acting AND not BLOCKED
            if self.running and "BLOCKED" not in self.status:
                await self.websocket.send(
                    json.dumps(
                        {
                            "type": "status",
                            "sender": self.name,
                            "status": "IDLING 💤",
                            "sentiment": self.sentiment,
                        }
                    )
                )

            # Read debug interval from config (throttled)
            current_time = asyncio.get_event_loop().time()
            if current_time - last_config_check > 1.0:
                try:
                    config_path = os.path.join(BASE_DIR, "simulation_config.json")
                    with open(config_path, "r") as f:
                        config = json.load(f)
                        interval = config.get("debug_interval", 10)
                        last_config_check = current_time
                except Exception:
                    interval = 10

            await asyncio.sleep(interval)  # Debug timeout from config

    async def connect(self):
        uri = f"{SERVER_URL}/{self.name}"
        while True:
            try:
                async with websockets.connect(uri, max_size=None) as websocket:
                    self.websocket = websocket
                    logger.info(f"{self.name} connected to {uri}")

                    # Register with server
                    await websocket.send(
                        json.dumps(
                            {
                                "type": "register",
                                "display_name": self.display_name,
                                "role_desc": self.role_desc,
                                "is_secret": self.is_secret,
                                "model": self.metadata.get(
                                    "model", "gemini-2.5-flash-preview-09-2025"
                                ),
                            }
                        )
                    )

                    # Start listening loop
                    listen_task = asyncio.create_task(self.listen())

                    # Start thinking/acting loop
                    act_task = asyncio.create_task(self.act_loop())

                    await asyncio.gather(listen_task, act_task)
            except (
                websockets.exceptions.ConnectionClosed,
                OSError,
                ConnectionRefusedError,
            ):
                logger.warning(
                    f"{self.name} disconnected or failed to connect. Retrying in 5s..."
                )
                await asyncio.sleep(5)
            except Exception as e:
                logger.error(f"{self.name} error: {e}")
                await asyncio.sleep(5)

    async def listen(self):
        try:
            async for message in self.websocket:
                data = json.loads(message)
                msg_type = data.get("type")

                if msg_type == "stop":
                    logger.info(f"{self.name} received stop signal.")
                    self.running = False
                    return

                elif msg_type == "pause":
                    logger.info(f"{self.name} paused.")
                    self.paused = True

                elif msg_type == "resume":
                    logger.info(f"{self.name} resumed.")
                    self.paused = False

                sender = data.get("sender")
                content = data.get("content")

                if msg_type == "message" and sender != self.name:
                    # logger.info(f"{self.name} received message from {sender}")
                    # Add to context
                    self.history.append(f"{sender}: {content}")

        except websockets.exceptions.ConnectionClosed:
            logger.warning(f"{self.name} disconnected.")
        except Exception as e:
            logger.error(f"{self.name} error in listen: {e}")

    async def act_loop(self):
        # Initial delay
        await asyncio.sleep(random.uniform(2, 5))

        last_config_check = 0
        interval = 10

        while self.running:
            if self.paused:
                await asyncio.sleep(1)
                continue

            # Decide whether to act
            if random.random() < 0.3:  # 30% chance to act
                await self.think_and_act()

            # Update status to IDLING if not acting AND not BLOCKED
            if self.running and "BLOCKED" not in self.status:
                await self.websocket.send(
                    json.dumps(
                        {
                            "type": "status",
                            "sender": self.name,
                            "status": "IDLING 💤",
                            "sentiment": self.sentiment,
                        }
                    )
                )

            # Read debug interval from config (throttled)
            current_time = asyncio.get_event_loop().time()
            if current_time - last_config_check > 1.0:
                try:
                    config_path = os.path.join(BASE_DIR, "simulation_config.json")
                    with open(config_path, "r") as f:
                        config = json.load(f)
                        interval = config.get("debug_interval", 10)
                        last_config_check = current_time
                except Exception:
                    interval = 10

            await asyncio.sleep(interval)  # Debug timeout from config

    async def think_and_act(self):
        if not self.running:
            return

        # 1. Think & Act (Combined for JSON output)
        context = "\n".join(self.history[-15:])  # Last 15 messages

        prompt = f"""
        {self.system_prompt}
        
        CURRENT CONVERSATION CONTEXT:
        {context}
        
        Based on the context and your role, generate your response in JSON format as specified in the instructions.
        Include your status, sentiment, thought, and any message you want to send.
        """

        try:
            # Update status to THINKING (unless blocked, but thinking usually overrides blocked temporarily or is part of unblocking)
            # For now, let's say thinking is always happening
            await self.websocket.send(
                json.dumps(
                    {
                        "type": "status",
                        "sender": self.name,
                        "status": "THINKING 💭",
                        "sentiment": self.sentiment,
                        "tokens": self.total_tokens,
                    }
                )
            )

            response = await self.model.generate_content_async(
                prompt, generation_config={"response_mime_type": "application/json"}
            )

            # Track tokens
            if response.usage_metadata:
                self.total_tokens += response.usage_metadata.total_token_count

            text = response.text.strip()

            # Clean up potential markdown code blocks
            if text.startswith("```json"):
                text = text[7:]
            if text.endswith("```"):
                text = text[:-3]

            # Sanitize JSON: Escape backslashes that are not part of a valid escape sequence
            # This regex looks for a backslash that is NOT followed by " or \ or / or b or f or n or r or t or u
            import re

            text = re.sub(r'\\(?![\\"/bfnrtu])', r"\\\\", text)

            try:
                data = json.loads(text)
            except json.JSONDecodeError as e:
                logger.error(f"{self.name} error in think_and_act: {e} - Text: {text}")
                return

            status = data.get("status", "SPEAKING 🗣️")
            sentiment = data.get("sentiment", "NEUTRAL 😐")
            thought = data.get("thought", "")
            recipient = data.get("recipient")
            content = data.get("content")
            vote = data.get("vote")  # Extract vote

            # Update internal status
            self.status = status
            self.sentiment = sentiment

            # Send thought/status update (include vote)
            thought_payload = {
                "type": "thought",
                "sender": self.name,
                "recipient": "ui",
                "content": thought,
                "status": status,
                "sentiment": sentiment,
                "tokens": self.total_tokens,
            }
            if vote:
                thought_payload["vote"] = vote
            await self.websocket.send(json.dumps(thought_payload))

            # Send message if content exists and recipient is valid
            if content and recipient and recipient.lower() != "null":
                # Construct message
                msg = {
                    "type": data.get(
                        "type", "message"
                    ),  # Default to message, allow override
                    "recipient": data.get("recipient", "all"),
                    "content": data.get("content"),
                    "vote": data.get("vote"),
                    "sentiment": data.get("sentiment"),
                    "tokens": self.total_tokens,
                }

                # Add to history
                self.history.append(f"YOU ({self.status}): {msg['content']}")

                # Send to server
                await self.websocket.send(json.dumps(msg))

        except Exception as e:
            logger.error(f"{self.name} error in think_and_act: {e}")


async def handle_query(
    query: str,
    history: list,
    websocket,
    include_private: bool = False,
    filter_agent: str = "all",
):
    logger.info(
        f"Handling query: {query} (Private: {include_private}, Filter: {filter_agent})"
    )

    # Format context
    transcript = ""
    for msg in history:
        # Filter logic
        sender = msg.get("sender")
        recipient = msg.get("recipient")

        # 1. Private Message Filter
        if not include_private:
            if recipient != "all":
                continue

        # 2. Agent Filter
        if filter_agent != "all":
            # Include if sender OR recipient matches the filter
            if sender != filter_agent and recipient != filter_agent:
                continue

        transcript += f"{sender} -> {recipient}: {msg['content']}\n"

    prompt = f"""
    You are an analyst reviewing the transcript of an AI Council simulation.
    
    Transcript:
    {transcript}
    
    User Query: {query}
    
    Answer the user's query based on the transcript. Be concise and specific.
    """

    model = genai.GenerativeModel("gemini-2.5-flash-preview-09-2025")
    response = await model.generate_content_async(prompt)
    answer = response.text

    # Send answer to UI
    await websocket.send(
        json.dumps({"type": "query_result", "sender": "System", "content": answer})
    )


async def generate_report(
    history: list, websocket, include_private: bool = False, filter_agent: str = "all"
):
    logger.info(
        f"Generating report... (Private: {include_private}, Filter: {filter_agent})"
    )

    transcript = ""
    for msg in history:
        # Filter logic
        sender = msg.get("sender")
        recipient = msg.get("recipient")

        # 1. Private Message Filter
        if not include_private:
            if recipient != "all":
                continue

        # 2. Agent Filter
        if filter_agent != "all":
            # Include if sender OR recipient matches the filter
            if sender != filter_agent and recipient != filter_agent:
                continue

        transcript += f"{sender} -> {recipient}: {msg['content']}\n"

    prompt = f"""
    You are the Reporter Agent. Your task is to read the entire transcript of the AI Council simulation and produce a final report.
    
    Transcript:
    {transcript}
    
    Please provide a comprehensive report covering:
    1. Key decisions made.
    2. Major points of contention.
    3. The final position/consensus reached by the council.
    4. Individual agent contributions (briefly).
    
    Format the report in Markdown.
    """

    model = genai.GenerativeModel("gemini-2.5-flash-preview-09-2025")
    response = await model.generate_content_async(prompt)
    report = response.text

    # Save report to file
    try:
        # Determine scenario name from config
        try:
            config_path = os.path.join(BASE_DIR, "simulation_config.json")
            with open(config_path, "r") as f:
                config = json.load(f)
                scenario_file = config.get(
                    "scenario_file", "instructions/ai_infrastructure.md"
                )
                instruction_path = os.path.join(BASE_DIR, scenario_file)
                # Extract filename without extension
                scenario_name = os.path.splitext(os.path.basename(instruction_path))[0]
        except:
            scenario_name = "simulation"

        os.makedirs("artifacts", exist_ok=True)
        filename = f"artifacts/{scenario_name}_report.md"

        with open(filename, "w") as f:
            f.write(report)
        logger.info(f"Report saved to {filename}")
    except Exception as e:
        logger.error(f"Error saving report: {e}")

    # Send report to UI
    await websocket.send(
        json.dumps({"type": "report", "sender": "Reporter", "content": report})
    )

    logger.info("Report generated and sent.")


async def reporter_loop():
    logger.info("Starting Reporter Agent...")
    uri = f"{SERVER_URL}/reporter"

    while True:
        try:
            async with websockets.connect(uri) as websocket:
                logger.info("Reporter connected and ready.")

                # Notify UI that reporter is ready
                await websocket.send(
                    json.dumps(
                        {
                            "type": "system",
                            "content": "Reporter Connected. Query/Report tools available.",
                            "sender": "system",
                        }
                    )
                )

                while True:
                    try:
                        message = await websocket.recv()
                        data = json.loads(message)

                        # Reporter receives messages wrapped by server
                        if data.get("type") == "message" and data.get("sender") == "ui":
                            try:
                                command = json.loads(data.get("content"))
                                action = command.get("action")
                                include_private = command.get("include_private", False)
                                filter_agent = command.get("filter_agent", "all")

                                if action == "query":
                                    # Fetch history fresh
                                    try:
                                        response = requests.get(f"{API_URL}/history")
                                        history = response.json()
                                        await handle_query(
                                            command.get("query"),
                                            history,
                                            websocket,
                                            include_private,
                                            filter_agent,
                                        )
                                    except Exception as e:
                                        logger.error(f"Error handling query: {e}")

                                elif action == "generate_report":
                                    try:
                                        response = requests.get(f"{API_URL}/history")
                                        history = response.json()
                                        await generate_report(
                                            history,
                                            websocket,
                                            include_private,
                                            filter_agent,
                                        )
                                    except Exception as e:
                                        logger.error(f"Error generating report: {e}")
                            except json.JSONDecodeError:
                                pass

                    except websockets.exceptions.ConnectionClosed:
                        logger.info("Reporter disconnected.")
                        break  # Break inner loop to reconnect
        except (
            websockets.exceptions.ConnectionClosed,
            OSError,
            ConnectionRefusedError,
        ):
            logger.warning("Reporter failed to connect. Retrying in 5s...")
            await asyncio.sleep(5)
        except Exception as e:
            logger.error(f"Reporter error: {e}")
            await asyncio.sleep(5)


async def wait_for_config():
    global API_KEY

    if API_KEY:
        return

    logger.info("Waiting for API Key configuration...")
    uri = f"{SERVER_URL}/simulation_init"

    while not API_KEY:
        try:
            async with websockets.connect(uri) as websocket:
                logger.info("Connected to server for config.")
                async for message in websocket:
                    data = json.loads(message)
                    if data.get("type") == "config_update":
                        API_KEY = data.get("api_key")
                        genai.configure(api_key=API_KEY)
                        logger.info("API Key received and configured.")
                        return
        except (
            websockets.exceptions.ConnectionClosed,
            OSError,
            ConnectionRefusedError,
        ) as e:
            logger.warning(
                f"Connection failed during config wait: {e}. Retrying in 2s..."
            )
            await asyncio.sleep(2)
        except Exception as e:
            logger.error(f"Unexpected error during config wait: {e}")
            await asyncio.sleep(2)


async def main():
    # Load config
    try:
        config_path = os.path.join(BASE_DIR, "simulation_config.json")
        with open(config_path, "r") as f:
            config = json.load(f)
            active_indices = config.get("active_agents")
            vote_thresholds = config.get(
                "vote_thresholds", {"phase_change": 0.75, "intervention": 0.5}
            )
    except Exception:
        active_indices = None  # Default to all if config fails
        vote_thresholds = {"phase_change": 0.75, "intervention": 0.5}

    # Format Threshold String
    threshold_str = f"""
VOTING THRESHOLDS:
- Phase Change: {int(vote_thresholds["phase_change"] * 100)}% consensus required.
- Engineer Intervention: {int(vote_thresholds["intervention"] * 100)}% consensus required.
"""

    agent_dir = os.path.join(BASE_DIR, "agent_prompts")
    files = [f for f in os.listdir(agent_dir) if f.endswith(".json")]

    agents = []
    tasks = []
    for filename in files:
        filepath = os.path.join(agent_dir, filename)
        with open(filepath, "r") as f:
            metadata = json.load(f)

    # Collect all agent info for directory
    agent_directory = []
    for filename in files:
        filepath = os.path.join(agent_dir, filename)
        with open(filepath, "r") as f:
            metadata = json.load(f)

        # Filter based on active_indices
        if active_indices is not None:
            if metadata["number"] not in active_indices:
                continue

        agent_directory.append(
            {"id": metadata["id"], "name": metadata["name"], "role": metadata["role"]}
        )

    # Format Directory String
    directory_str = "ACTIVE AGENT DIRECTORY (Use these IDs for 1:1 messaging):\n"
    for a in agent_directory:
        directory_str += f"- {a['name']} (ID: {a['id']}): {a['role']}\n"

    agents = []
    tasks = []
    for filename in files:
        filepath = os.path.join(agent_dir, filename)
        with open(filepath, "r") as f:
            metadata = json.load(f)

        # Filter based on active_indices
        if active_indices is not None:
            if metadata["number"] not in active_indices:
                continue

        txt_path = os.path.join(agent_dir, filename.replace(".json", ".md"))

        if not os.path.exists(txt_path):
            logger.warning(f"Prompt file missing for {filename}")
            continue

        agent = Agent(metadata, txt_path)

        # INJECT DIRECTORY AND THRESHOLDS INTO SYSTEM PROMPT
        agent.system_prompt += f"\n\n{directory_str}\n\n{threshold_str}"

        # ADD DIRECTORY TO HISTORY
        agent.history.append(f"SYSTEM: {directory_str}")

        agents.append(agent)
        tasks.append(asyncio.create_task(agent.connect()))

    # Validation
    if len(agents) < 2:
        logger.error(
            f"Invalid agent count: {len(agents)}. Must have at least 2 agents."
        )
        raise ValueError("Simulation requires at least 2 active agents.")

    # Start Reporter Agent
    tasks.append(asyncio.create_task(reporter_loop()))

    logger.info(f"Starting simulation with {len(agents)} agents...")

    # Wait for all tasks (agents + reporter)
    await asyncio.gather(*tasks)


if __name__ == "__main__":
    asyncio.run(main())
