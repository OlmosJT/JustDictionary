package uz.gita.justdictionary.presenter.viewmodel

import android.database.Cursor
import androidx.lifecycle.LiveData

interface SearchViewModel {
    val allWordsLiveData: LiveData<Cursor>
    val completeLiveData: LiveData<Unit>

    fun loadAllWords()
    fun onCLickRememberBtn(id: Int)
    fun searchWord(query: String)
}