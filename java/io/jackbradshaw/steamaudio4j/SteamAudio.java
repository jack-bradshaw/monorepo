package io.jackbradshaw.steamaudio4j;

import com.google.devtools.build.runfiles.Runfiles;

/** Utility to load the SteamAudio (libphonon) library with JNI. */
class SteamAudio {
    
    private static Object lock = new Object();
    private static boolean loaded = false;
    
    /** Loads the library synchronously. Returns normally if the library is already loaded. */
    public static void loadLibrary() {
        synchronized(lock) {
            if (loaded) {
                return;
            }
            System.load(Runfiles.preload().unmapped().rlocation(getLibraryPath()));
            loaded = true;
        }
    }

    private static String getLibraryPath() {
        String base = "io_jackbradshaw/third_party/valvesoftware/steamaudio/libs/";
        return base + getLibraryOsPathExtension();
        
    }

    public static String getLibraryOsPathExtension() {
        String osName = System.getProperty("os.name").toLowerCase();
        String osArch = System.getProperty("os.arch").toLowerCase();

        switch (osName) {
            case "osx":
                return "macos";
            case "linux":
                switch (osArch) {
                    case "amd64":
                        return "linux-x64";
                    case "x86":
                        return "linux-x86";
                    default:
                        throw new RuntimeException("Unsupported Linux architecture: " + osArch);
                }
            case "windows":
                switch (osArch) {
                    case "amd64":
                        return "windowsx64";
                    case "x86":
                        return "windowsx86";
                    default:
                        throw new RuntimeException("Unsupported Windows architecture: " + osArch);
                }
            case "android":
                switch (osArch) {
                    case "armv7l":
                        return "android-armv7";
                    case "aarch64":
                        return "android-armv8";
                    case "i686":
                        return "android-x86";
                    case "x86_64":
                        return "android-x64";
                    default:
                        throw new RuntimeException("Unsupported Android architecture: " + osArch);
                }
            default:
                throw new RuntimeException("Unsupported operating system: " + osName);
        }
    }
}