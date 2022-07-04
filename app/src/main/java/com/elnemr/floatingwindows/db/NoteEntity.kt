package com.elnemr.floatingwindows.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.elnemr.floatingwindows.util.Constants

@Entity(tableName = Constants.NOTE_TABLE_NAME)
data class NoteEntity(
    val note: String = ""
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}