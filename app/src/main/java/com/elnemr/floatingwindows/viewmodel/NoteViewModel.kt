package com.elnemr.floatingwindows.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elnemr.floatingwindows.db.NoteEntity
import com.elnemr.floatingwindows.repo.Repo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val repo: Repo
) :
    ViewModel() {

    suspend fun loadItemsFromDb(): Flow<List<NoteEntity>> {
        val request = viewModelScope.async { repo.fetchNotes() }
        return request.await()
    }

    fun insertNote(note: String) {
        viewModelScope.launch {
            repo.insertNote(NoteEntity(note))
        }
    }

    fun deleteNote(note: NoteEntity) {
        viewModelScope.launch {
            repo.deleteNote(note)
        }
    }
}