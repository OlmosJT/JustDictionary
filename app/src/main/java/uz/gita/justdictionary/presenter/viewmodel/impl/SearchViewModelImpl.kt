package uz.gita.justdictionary.presenter.viewmodel.impl

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.justdictionary.domain.usecase.AppUseCase
import uz.gita.justdictionary.presenter.viewmodel.SearchViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModelImpl @Inject constructor(
    private val useCase: AppUseCase
): ViewModel(), SearchViewModel {
    override val allWordsLiveData = MutableLiveData<Cursor>()
    override val completeLiveData = MutableLiveData<Unit>()
    override val isLoadingLiveData = MutableLiveData<Boolean>()

    override fun loadAllWords() {
        isLoadingLiveData.value = true
        useCase.getAllWords().onEach {
            delay(100)
            allWordsLiveData.value = it
            isLoadingLiveData.postValue(false)
        }.launchIn(viewModelScope)
    }

    override fun onCLickRememberBtn(id: Long, isRemember: Int) {
        if(isRemember == 0) {
            useCase.rememberWord(id).onEach {
                completeLiveData.value = Unit
            }.launchIn(viewModelScope)
        } else if(isRemember == 1) {
            useCase.forgetWord(id).onEach {
                completeLiveData.value = Unit
            }.launchIn(viewModelScope)
        }
    }

    override fun searchWord(query: String) {
        if(query.isNotEmpty()){
            isLoadingLiveData.value = true
            useCase.searchWord(query).onEach {
                delay(100)
                allWordsLiveData.value = it
                isLoadingLiveData.postValue(false)
            }.launchIn(viewModelScope)
        }
        else loadAllWords()
    }

}