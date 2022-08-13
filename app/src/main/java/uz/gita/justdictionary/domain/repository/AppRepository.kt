package uz.gita.justdictionary.domain.repository

import android.database.Cursor
import kotlinx.coroutines.flow.Flow

interface AppRepository {
    suspend fun getAllWords(): Cursor

    suspend fun rememberWord(id: Int)
    suspend fun forgetWord(id:Int)

    suspend fun searchWord(query: String): Cursor
}