package io.matthewbradshaw.merovingian.testing;

import com.jme3.app.SimpleApplication;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.material.RenderState.BlendMode;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;
import com.jme3.texture.Texture;
import com.jme3.util.TangentBinormalGenerator;

/** Sample 6 - how to give an object's surface a material and texture.
 * How to make objects transparent. How to make bumpy and shiny surfaces.  */
public class Thing extends SimpleApplication {

    public static void main(String[] args) {
        Thing app = new Thing();
        app.start();
    }

    @Override
    public void simpleInitApp() {

        /** A simple textured cube -- in good MIP map quality. */
        Box cube1Mesh = new Box( 1f,1f,1f);
        Geometry cube1Geo = new Geometry("My Textured Box", cube1Mesh);
        cube1Geo.setLocalTranslation(new Vector3f(-3f,1.1f,0f));
        Material cube1Mat = new Material(assetManager,
                "Common/MatDefs/Misc/Unshaded.j3md");
        Texture cube1Tex = assetManager.loadTexture(
                "Interface/Logo/Monkey.jpg");
        cube1Mat.setTexture("ColorMap", cube1Tex);
        cube1Geo.setMaterial(cube1Mat);
        rootNode.attachChild(cube1Geo);

        /** A translucent/transparent texture, similar to a window frame. */
        Box cube2Mesh = new Box( 1f,1f,0.01f);
        Geometry cube2Geo = new Geometry("window frame", cube2Mesh);
        Material cube2Mat = new Material(assetManager,
                "Common/MatDefs/Misc/Unshaded.j3md");
        cube2Mat.setTexture("ColorMap",
                assetManager.loadTexture("Textures/ColoredTex/Monkey.png"));
        cube2Mat.getAdditionalRenderState().setBlendMode(BlendMode.Alpha);
        cube2Geo.setQueueBucket(Bucket.Transparent);
        cube2Geo.setMaterial(cube2Mat);
        rootNode.attachChild(cube2Geo);

        /** A bumpy rock with a shiny light effect.*/
        Sphere sphereMesh = new Sphere(32,32, 2f);
        Geometry sphereGeo = new Geometry("Shiny rock", sphereMesh);
        sphereMesh.setTextureMode(Sphere.TextureMode.Projected); // better quality on spheres
        TangentBinormalGenerator.generate(sphereMesh);           // for lighting effect
        Material sphereMat = new Material(assetManager,
                "Common/MatDefs/Light/Lighting.j3md");
        sphereMat.setTexture("DiffuseMap",
                assetManager.loadTexture("Textures/Terrain/Pond/Pond.jpg"));
        sphereMat.setTexture("NormalMap",
                assetManager.loadTexture("Textures/Terrain/Pond/Pond_normal.png"));
        sphereMat.setBoolean("UseMaterialColors",true);
        sphereMat.setColor("Diffuse",ColorRGBA.White);
        sphereMat.setColor("Specular",ColorRGBA.White);
        sphereMat.setFloat("Shininess", 64f);  // [0,128]
        sphereGeo.setMaterial(sphereMat);
        sphereGeo.setLocalTranslation(0,2,-2); // Move it a bit
        sphereGeo.rotate(1.6f, 0, 0);          // Rotate it a bit
        rootNode.attachChild(sphereGeo);

        /** Must add a light to make the lit object visible! */
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(1,0,-2).normalizeLocal());
        sun.setColor(ColorRGBA.White);
        rootNode.addLight(sun);

    }
}