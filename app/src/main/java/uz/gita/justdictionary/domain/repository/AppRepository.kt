package uz.gita.justdictionary.domain.repository

import android.database.Cursor
import kotlinx.coroutines.flow.Flow

interface AppRepository {
    suspend fun getAllWords(): Cursor

    suspend fun rememberWord(id: Long)
    suspend fun forgetWord(id: Long)

    suspend fun searchWord(query: String): Cursor
}