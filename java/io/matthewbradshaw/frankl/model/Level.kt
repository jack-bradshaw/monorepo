package java.io.matthewbradshaw.frankl.model

interface Level {
  val root: LevelItem
  suspend fun restore(snapshot: LevelSnapshot)
  suspend fun snapshot(): LevelSnapshot
}