package com.elnemr.floatingwindows.adapter.run

import androidx.recyclerview.widget.DiffUtil
import com.elnemr.floatingwindows.db.NoteEntity

object NoteDiffCallBack : DiffUtil.ItemCallback<NoteEntity>() {
    override fun areItemsTheSame(oldItem: NoteEntity, newItem: NoteEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: NoteEntity, newItem: NoteEntity): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }
}