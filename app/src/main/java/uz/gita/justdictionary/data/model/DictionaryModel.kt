package uz.gita.justdictionary.data.model

data class DictionaryModel(
    val id: Long,
    val word: String,
    val wordtype: String,
    val definition: String,
    var isRemember: Int
)
