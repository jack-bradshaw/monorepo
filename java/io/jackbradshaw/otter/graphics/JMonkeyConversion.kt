package io.jackbradshaw.otter.graphics

import com.jme3.math.ColorRGBA
import com.jme3.math.Vector3f
import com.jme3.scene.VertexBuffer
import com.jme3.util.BufferUtils
import io.jackbradshaw.otter.math.toJMonkeyVector
import com.jme3.scene.Mesh as JmeMesh

/** Creates a new JMonkey Engine 3 Color which is equivalent to this. */
fun Color.toJMonkeyColor() = ColorRGBA(red, green, blue, alpha)

/** Creates a new otter [Color] which is equivalent to this. */
fun ColorRGBA.tootterColor() = color(red = r, green = g, blue = b, alpha = a)

/** Creates a new JMonkey Engine 3 Color which is equivalent to this. */
fun Mesh.toJMonkeyMesh(): JmeMesh {
  val vertices: Array<Vector3f> = vertexList.map { it.toJMonkeyVector() }.toTypedArray()
  val ordering: IntArray =
      orderList.flatMap { listOf(it.index0, it.index1, it.index2) }.toIntArray()
  return JmeMesh().apply {
    setBuffer(VertexBuffer.Type.Position, 3, BufferUtils.createFloatBuffer(*vertices))
    setBuffer(VertexBuffer.Type.Index, 3, BufferUtils.createIntBuffer(*ordering))
    updateBound()
  }
}

/** Creates a new otter [Color] which is equivalent to this. */
fun JmeMesh.tootterMesh(): Mesh = TODO()
