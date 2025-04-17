package com.example.pupilmeshassignment.presentation.screens.manga

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pupilmeshassignment.domain.useCase.manga.GetMangaListUseCase
import com.example.pupilmeshassignment.domain.useCase.signin.SignOutUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MangaViewModel(
    private val getMangaListUseCase: GetMangaListUseCase,
    private val signOutUseCase: SignOutUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(MangaState())
    val state: StateFlow<MangaState> = _state

    fun loadManga() {
        if (_state.value.isLoading || _state.value.isEndReached) return

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            getMangaListUseCase(_state.value.page, 25)
                .catch { e ->
                    _state.update { it.copy(isLoading = false) }
                    Log.e("MangaViewModel", "Load error: ${e.localizedMessage}", e)
                }
                .collectLatest { mangaList ->
                    val isEnd = mangaList.isEmpty()
                    _state.update {
                        it.copy(
                            mangaList = it.mangaList + mangaList,
                            isLoading = false,
                            page = if (!isEnd) it.page + 1 else it.page,
                            isEndReached = isEnd
                        )
                    }
                }
        }
    }

    fun logout() {
        viewModelScope.launch {
            signOutUseCase()
        }
    }
}



