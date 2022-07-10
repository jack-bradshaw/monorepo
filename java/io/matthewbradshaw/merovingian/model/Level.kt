package java.io.matthewbradshaw.merovingian.model

interface Level {
  val root: LevelItem
  suspend fun restore(snapshot: LevelSnapshot)
  suspend fun snapshot(): LevelSnapshot
}