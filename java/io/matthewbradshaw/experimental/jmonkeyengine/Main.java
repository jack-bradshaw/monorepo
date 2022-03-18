package io.matthewbradshaw.experimental.jmonkeyengine;

import com.jme3.system.AppSettings;

public class Main {
  public static void main(String[] args) {
    App app = new App();
    AppSettings settings = new AppSettings(true);
    settings.setTitle("Hello, World!");
    app.setSettings(settings);
    app.start();
  }
}
