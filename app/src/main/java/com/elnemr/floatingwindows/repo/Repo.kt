package com.elnemr.floatingwindows.repo

import android.content.Intent
import com.elnemr.floatingwindows.db.NoteDao
import com.elnemr.floatingwindows.db.NoteEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Repo @Inject constructor(private val noteDao: NoteDao) {

    suspend fun deleteNote(noteEntity: NoteEntity) = noteDao.deleteNote(noteEntity)
    suspend fun insertNote(noteEntity: NoteEntity) = noteDao.insertNote(noteEntity)
    fun fetchNotes() : Flow<List<NoteEntity>> = noteDao.fetchNotes()

//    fun update() {
//        context.sendBroadcast(Intent(NOTES_RECEIVER_ACTION))
//    }
}