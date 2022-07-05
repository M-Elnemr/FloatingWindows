package com.elnemr.floatingwindows.adapter.base

import com.elnemr.floatingwindows.db.NoteEntity

interface OnItemClickInterface {
    fun onDeleteClicked(note: NoteEntity)
}