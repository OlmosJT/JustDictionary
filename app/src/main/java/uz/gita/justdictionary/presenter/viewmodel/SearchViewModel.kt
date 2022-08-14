package uz.gita.justdictionary.presenter.viewmodel

import android.database.Cursor
import androidx.lifecycle.LiveData

interface SearchViewModel {
    val allWordsLiveData: LiveData<Cursor>
    val completeLiveData: LiveData<Unit>
    val isLoadingLiveData: LiveData<Boolean>

    fun loadAllWords()
    fun onCLickRememberBtn(id: Long, isRemember: Int)
    fun searchWord(query: String)
}