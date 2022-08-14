package uz.gita.justdictionary.domain.usecase.impl

import android.database.Cursor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.gita.justdictionary.domain.repository.AppRepository
import uz.gita.justdictionary.domain.usecase.AppUseCase
import javax.inject.Inject

class AppUseCaseImpl @Inject constructor(
    private val repository: AppRepository
): AppUseCase {
    override fun getAllWords() = flow<Cursor> {
        emit(repository.getAllWords())
    }.flowOn(Dispatchers.IO)

    override fun searchWord(query: String) = flow<Cursor> {
        emit(repository.searchWord(query))
    }.flowOn(Dispatchers.IO)

    override fun rememberWord(id: Long) = flow<Unit> {
        emit(repository.rememberWord(id))
    }.flowOn(Dispatchers.IO)

    override fun forgetWord(id: Long) = flow<Unit> {
        emit(repository.forgetWord(id))
    }.flowOn(Dispatchers.IO)
}