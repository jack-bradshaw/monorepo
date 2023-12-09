package io.jackbradshaw.swigtest;

import java.io.File
import com.google.devtools.build.runfiles.Runfiles
import IPLContextSettings
import IPLPathEffectSettings

 class MainKt {
    fun doThing()  {
       val preloaded = Runfiles.preload();
       val path = preloaded.unmapped().rlocation("io_jackbradshaw/java/io/jackbradshaw/swigtest/libphonon/libwrap.dylib")
            
        System.load(path)
        println(IPLPathEffectSettings().getMaxOrder())
        throw RuntimeException("end")
        // String manifestPath = runfiles.rlocation("MANIFEST"); // Replace with actual filename
        // try (BufferedReader reader = new BufferedReader(new FileReader(manifestPath))) {
        //     String line;
        //     while ((line = reader.readLine()) != null) {
        //         if (!line.contains("phonon")) {
        //             continue;
        //         }
        //         System.out.println(line);
        //     }
        // }
    }
}