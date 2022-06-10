/*
VR Instancing Progress:
- igByGeom gets huge, and updateInstances still happens on it,
  even if the associated geometry is no longer in the scene
  - track which InstanceGeometry to add or remove inside the Geometry?
  - have list of instances to render maintained somewhere else?
*/
package io.matthewbradshaw.omniverse.vrexample

import com.jme3.input.InputManager
import com.jme3.input.KeyInput
import com.jme3.input.controls.KeyTrigger
import com.jme3.app.SimpleApplication
import com.jme3.material.Material
import com.jme3.math.FastMath
import com.jme3.math.Quaternion
import com.jme3.math.Vector2f
import com.jme3.math.Vector3f
import com.jme3.post.FilterPostProcessor
import com.jme3.scene.Geometry
import com.jme3.scene.Node
import com.jme3.scene.Spatial
import com.jme3.scene.Spatial.CullHint
import com.jme3.texture.Texture
import com.jme3.texture.Texture.MagFilter
import com.jme3.texture.Texture.MinFilter
import com.jme3.ui.Picture
import com.jme3.util.SkyFactory
import com.jme3.input.vr.openvr.OpenVRInput
import com.jme3.post.CartoonSSAO
import com.jme3.util.VRGuiManager
import com.jme3.input.vr.VRMouseManager
import com.jme3.system.jopenvr.VRControllerAxis_t
import com.jme3.system.jopenvr.VRControllerState_t
import com.jme3.input.controls.ActionListener
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import com.jme3.scene.shape.Box

class TestjMonkeyVR : SimpleApplication() {
  // general objects for scene management
  var boxes: Node = Node("boxes")
  var observer: Spatial? = null
  var moveForward = false
  var moveBackwards = false
  var rotateLeft = false
  var rotateRight = false
  lateinit var mat: Material
  var leftHand: Geometry? = null
  var rightHand: Geometry? = null

  override fun simpleInitApp() {
    initTestScene()

    // print out what device we have
    if (VRApplication.getVRHardware() != null) {
      System.out.println("Attached device: " + VRApplication.getVRHardware().getType())
    }
  }

  private fun initTestScene() {
    observer = Node("observer")
    val sky: Spatial = SkyFactory.createSky(
      getAssetManager(), "Textures/Sky/Bright/spheremap.png", SkyFactory.EnvMapType.EquirectMap
    )
    rootNode.attachChild(sky)
    val box = Geometry("", Box(5f, 5f, 5f))
    mat = Material(getAssetManager(), "jmevr/shaders/Unshaded.j3md")
    val noise: Texture = getAssetManager().loadTexture("Textures/noise.png")
    noise.setMagFilter(MagFilter.Nearest)
    noise.setMinFilter(MinFilter.Trilinear)
    noise.setAnisotropicFilter(16)
    mat.setTexture("ColorMap", noise)

    // make the floor according to the size of our play area
    val floor = Geometry("floor", Box(1f, 1f, 1f))
    val playArea: Vector2f = null//OpenVRBounds.getPlaySize()
    if (playArea == null) {
      // no play area, use default size & height
      floor.setLocalScale(2f, 0.5f, 2f)
      floor.move(0f, -1.5f, 0f)
    } else {
      // cube model is actually 2x as big, cut it down to proper playArea size with * 0.5
      floor.setLocalScale(playArea.x * 0.5f, 0.5f, playArea.y * 0.5f)
      floor.move(0f, -0.5f, 0f)
    }
    floor.setMaterial(mat)
    rootNode.attachChild(floor)

    // hand wands
    leftHand = getAssetManager().loadModel("Models/vive_controller.j3o") as Geometry
    rightHand = leftHand.clone()
    val handMat = Material(getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md")
    handMat.setTexture("ColorMap", getAssetManager().loadTexture("Textures/vive_controller.png"))
    leftHand.setMaterial(handMat)
    rightHand.setMaterial(handMat)
    rootNode.attachChild(rightHand)
    rootNode.attachChild(leftHand)

    // gui element
    val guiCanvasSize: Vector2f = VRGuiManager.getCanvasSize()
    val test = Picture("testpic")
    //test.setImage(getAssetManager(), "Textures/crosshair.png", true);
    test.setImage(getAssetManager(), "Textures/happy.png", true)
    test.setWidth(192f)
    test.setHeight(128f)
    test.setPosition(guiCanvasSize.x * 0.5f - 192f * 0.5f, guiCanvasSize.y * 0.5f - 128f * 0.5f)
    guiNode.attachChild(test)

    // test any positioning mode here (defaults to AUTO_CAM_ALL)
    VRGuiManager.setPositioningMode(POSITIONING_MODE.AUTO_CAM_ALL_SKIP_PITCH)
    VRGuiManager.setGuiScale(0.4f)
    VRGuiManager.setPositioningElasticity(10f)
    box.setMaterial(mat)
    val box2: Geometry = box.clone()
    box2.move(15, 0, 0)
    box2.setMaterial(mat)
    val box3: Geometry = box.clone()
    box3.move(-15, 0, 0)
    box3.setMaterial(mat)
    boxes.attachChild(box)
    boxes.attachChild(box2)
    boxes.attachChild(box3)
    rootNode.attachChild(boxes)
    observer.setLocalTranslation(Vector3f(0.0f, 0.0f, 0.0f))
    VRApplication.setObserver(observer)
    rootNode.attachChild(observer)
    addAllBoxes()
    initInputs()

    // use magic VR mouse cusor (same usage as non-VR mouse cursor)
    getInputManager().setCursorVisible(true)
    if (MAKE_CONTROLLER_TEXT_FILE) {
      controllerTextFile = File("controllerinfo.txt")
    }
  }

  private fun initInputs() {
    val inputManager: InputManager = getInputManager()
    inputManager.addMapping("toggle", KeyTrigger(KeyInput.KEY_SPACE))
    inputManager.addMapping("incShift", KeyTrigger(KeyInput.KEY_Q))
    inputManager.addMapping("decShift", KeyTrigger(KeyInput.KEY_E))
    inputManager.addMapping("forward", KeyTrigger(KeyInput.KEY_W))
    inputManager.addMapping("back", KeyTrigger(KeyInput.KEY_S))
    inputManager.addMapping("left", KeyTrigger(KeyInput.KEY_A))
    inputManager.addMapping("right", KeyTrigger(KeyInput.KEY_D))
    inputManager.addMapping("filter", KeyTrigger(KeyInput.KEY_F))
    val acl: ActionListener = object : ActionListener {
      fun onAction(name: String, keyPressed: Boolean, tpf: Float) {
        if (name == "incShift" && keyPressed) {
          VRGuiManager.adjustGuiDistance(-0.1f)
        } else if (name == "decShift" && keyPressed) {
          VRGuiManager.adjustGuiDistance(0.1f)
        } else if (name == "filter" && keyPressed) {
          // adding filters in realtime
          val cartfilt = CartoonSSAO()
          val fpp = FilterPostProcessor(getAssetManager())
          fpp.addFilter(cartfilt)
          getViewPort().addProcessor(fpp)
          // filters added to main viewport during runtime,
          // move them into VR processing
          // (won't do anything if not in VR mode)
          VRApplication.moveScreenProcessingToVR()
        }
        if (name == "toggle") {
          VRGuiManager.positionGui()
        }
        if (name == "forward") {
          moveForward = keyPressed
        } else if (name == "back") {
          moveBackwards = keyPressed
        } else if (name == "left") {
          rotateLeft = keyPressed
        } else if (name == "right") {
          rotateRight = keyPressed
        }
      }
    }
    inputManager.addListener(acl, "forward")
    inputManager.addListener(acl, "back")
    inputManager.addListener(acl, "left")
    inputManager.addListener(acl, "right")
    inputManager.addListener(acl, "toggle")
    inputManager.addListener(acl, "incShift")
    inputManager.addListener(acl, "decShift")
    inputManager.addListener(acl, "filter")
    inputManager.addListener(acl, "dumpImages")
  }

  private var distance = 100f
  private var prod = 0f
  private var placeRate = 0f

  //FPS test
  private val tpfAdder = 0f
  private val tpfCount = 0
  override fun simpleUpdate(tpf: Float) {
    prod += tpf
    distance = 100f * FastMath.sin(prod)
    boxes.setLocalTranslation(0, 0, 200f + distance)
    if (moveForward) {
      observer.move(VRApplication.getFinalObserverRotation().getRotationColumn(2).mult(tpf * 8f))
    }
    if (moveBackwards) {
      observer.move(VRApplication.getFinalObserverRotation().getRotationColumn(2).mult(-tpf * 8f))
    }
    if (rotateLeft) {
      observer.rotate(0, 0.75f * tpf, 0)
    }
    if (rotateRight) {
      observer.rotate(0, -0.75f * tpf, 0)
    }

    // use the analog control on the first tracked controller to push around the mouse
    VRMouseManager.updateAnalogAsMouse(0, null, null, null, tpf)
    handleWandInput(0, leftHand)
    handleWandInput(1, rightHand)
    if (placeRate > 0f) placeRate -= tpf
  }

  private fun handleWandInput(index: Int, geo: Geometry?) {
    if (VRApplication.getVRinput() == null) return
    val q: Quaternion = VRApplication.getVRinput().getFinalObserverRotation(index)
    val v: Vector3f = VRApplication.getVRinput().getFinalObserverPosition(index)
    if (q != null && v != null) {
      geo.setCullHint(CullHint.Dynamic) // make sure we see it
      geo.setLocalTranslation(v)
      geo.setLocalRotation(q)
      // place boxes when holding down trigger
      if (VRApplication.getVRinput().getAxis(index, VRINPUT_TYPE.ViveTriggerAxis).x >= 0.8f &&
        placeRate <= 0f
      ) {
        placeRate = 0.5f
        addBox(v, q, 0.1f)
        VRApplication.getVRinput().triggerHapticPulse(index, 0.1f)
      }
      // print out all of the known information about the controllers here to file
      if (MAKE_CONTROLLER_TEXT_FILE) {
        var out = ""
        val rawstate: VRControllerState_t =
          (VRApplication.getVRinput() as OpenVRInput).getRawControllerState(index) as VRControllerState_t
        rawstate.read()
        for (i in 0 until rawstate.rAxis.length) {
          val cs: VRControllerAxis_t = rawstate.rAxis.get(i)
          cs.read()
          out += """Controller#${Integer.toString(index)}, Axis#${Integer.toString(i)} X: ${
            java.lang.Float.toString(
              cs.x
            )
          }, Y: ${java.lang.Float.toString(cs.y)}
"""
        }
        out += """
                Button press: ${java.lang.Long.toString(rawstate.ulButtonPressed)}, touch: ${
          java.lang.Long.toString(
            rawstate.ulButtonTouched
          )
        }
                
                """.trimIndent()
        var writer: BufferedWriter? = null
        try {
          writer = BufferedWriter(FileWriter(controllerTextFile, true))
          writer.write(out)
        } catch (e: Exception) {
        }
        try {
          writer!!.close()
        } catch (e: Exception) {
        }
      }
    } else {
      geo.setCullHint(CullHint.Always) // hide it
    }
  }

  private fun addAllBoxes() {
    val distance = 8f
    for (x in 0..34) {
      val cos: Float = FastMath.cos(x * FastMath.PI / 16f) * distance
      val sin: Float = FastMath.sin(x * FastMath.PI / 16f) * distance
      var loc = Vector3f(cos, 0, sin)
      addBox(loc, null, 1f)
      loc = Vector3f(0, cos, sin)
      addBox(loc, null, 1f)
    }
  }

  private fun addBox(location: Vector3f, rot: Quaternion?, scale: Float) {
    val leftQuad = Geometry("Box", smallBox)
    if (rot != null) {
      leftQuad.setLocalRotation(rot)
    } else {
      leftQuad.rotate(0.5f, 0f, 0f)
    }
    leftQuad.setLocalScale(scale)
    leftQuad.setMaterial(mat)
    leftQuad.setLocalTranslation(location)
    rootNode.attachChild(leftQuad)
  }

  companion object {
    const val MAKE_CONTROLLER_TEXT_FILE =
      true // makes a text file with all controller output, slows things down but good for data collection
    var controllerTextFile: File? = null

    @JvmStatic
    fun main() {
      val game = VrGame()
      game.preconfigureVRApp(
        PRECONFIG_PARAMETER.USE_VR_COMPOSITOR,
        true
      )
      test.preconfigureVRApp(
        PRECONFIG_PARAMETER.ENABLE_MIRROR_WINDOW,
        false
      ) // runs faster when set to false, but will allow mirroring
      test.preconfigureVRApp(PRECONFIG_PARAMETER.FORCE_VR_MODE, false) // render two eyes, regardless of API detection
      test.preconfigureVRApp(PRECONFIG_PARAMETER.SET_GUI_CURVED_SURFACE, true)
      test.preconfigureVRApp(PRECONFIG_PARAMETER.FLIP_EYES, false)
      test.preconfigureVRApp(PRECONFIG_PARAMETER.SET_GUI_OVERDRAW, true) // show gui even if it is behind things
      test.preconfigureVRApp(
        PRECONFIG_PARAMETER.INSTANCE_VR_RENDERING,
        false
      ) // faster VR rendering, requires some vertex shader changes (see jmevr/shaders/Unshaded.j3md)
      test.preconfigureVRApp(PRECONFIG_PARAMETER.NO_GUI, false)
      test.preconfigureFrustrumNearFar(0.1f, 512f) // set frustum distances here before app starts
      //test.preconfigureResolutionMultiplier(0.666f); // you can downsample for performance reasons
      test.start()
    }

    private val smallBox = Box(0.3f, 0.3f, .3f)
  }
}