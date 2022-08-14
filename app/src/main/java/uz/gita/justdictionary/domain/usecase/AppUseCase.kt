package uz.gita.justdictionary.domain.usecase

import android.database.Cursor
import kotlinx.coroutines.flow.Flow

interface AppUseCase {
    fun getAllWords(): Flow<Cursor>
    fun searchWord(query: String): Flow<Cursor>

    fun rememberWord(id: Long): Flow<Unit>
    fun forgetWord(id: Long): Flow<Unit>
}