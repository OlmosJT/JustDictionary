package uz.gita.justdictionary.domain.repository.impl

import android.database.Cursor
import uz.gita.justdictionary.data.local.dao.DictionaryDao
import uz.gita.justdictionary.domain.repository.AppRepository
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val dao: DictionaryDao
): AppRepository {
    override suspend fun getAllWords(): Cursor = dao.getAllWords()
    override suspend fun searchWord(query: String): Cursor = dao.searchWord(query)

    override suspend fun rememberWord(id: Long) {
        dao.rememberWord(id)
    }

    override suspend fun forgetWord(id: Long) {
        dao.forgetWord(id)
    }

}