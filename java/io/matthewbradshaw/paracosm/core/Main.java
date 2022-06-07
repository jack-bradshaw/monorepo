package io.matthewbradshaw.paracosm.core;

import com.jme3.system.AppSettings;
import com.jme3.app.state.AppState;

import com.jme3.app.VRAppState;
import com.jme3.app.VRConstants;
import com.jme3.app.VREnvironment;
import com.jme3.app.LostFocusBehavior;

public class Main {
  public static void main(String[] args) {
    AppSettings settings = new AppSettings(/* loadDefaults= */ true);
    settings.put(VRConstants.SETTING_VRAPI, VRConstants.SETTING_VRAPI_OPENVR_LWJGL_VALUE);
    settings.put(VRConstants.SETTING_ENABLE_MIRROR_WINDOW, true);

    VREnvironment env = new VREnvironment(settings);
    env.initialize();

    if (env.isInitialized()){
      VRAppState vrAppState = new VRAppState(settings, env);
      vrAppState.setMirrorWindowSize(1024, 800);
      App app = new App(vrAppState);
      app.setLostFocusBehavior(LostFocusBehavior.Disabled);
      app.setSettings(settings);
      app.setShowSettings(false);
      app.start();
    } else {
      throw new IllegalStateException("Root environment initialization failed.");
    }
  }
}
