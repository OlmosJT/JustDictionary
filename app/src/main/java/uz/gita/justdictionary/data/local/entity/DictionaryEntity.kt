package uz.gita.justdictionary.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "entries")
data class DictionaryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val word: String,
    val wordtype: String,
    val definition: String,
    var isRemember: Int
)
