package uz.gita.justdictionary.presenter.viewmodel.impl

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.justdictionary.domain.repository.AppRepository
import uz.gita.justdictionary.domain.usecase.AppUseCase
import uz.gita.justdictionary.presenter.viewmodel.SearchViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModelImpl @Inject constructor(
    private val useCase: AppUseCase
): ViewModel(), SearchViewModel {
    override val allWordsLiveData = MutableLiveData<Cursor>()
    override val completeLiveData = MutableLiveData<Unit>()

    override fun loadAllWords() {
        useCase.getAllWords().onEach {
            allWordsLiveData.value = it
        }.launchIn(viewModelScope)
    }

    override fun onCLickRememberBtn(id: Int) {
        if(id == 0) {
            useCase.rememberWord(id).onEach {
                completeLiveData.value = Unit
            }.launchIn(viewModelScope)
        } else {
            useCase.forgetWord(id).onEach {
                completeLiveData.value = Unit
            }.launchIn(viewModelScope)
        }
    }

    override fun searchWord(query: String) {
        useCase.searchWord(query).onEach {
            allWordsLiveData.value = it
        }.launchIn(viewModelScope)
    }

}