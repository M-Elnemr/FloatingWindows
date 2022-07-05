package com.elnemr.floatingwindows.adapter.run

import com.elnemr.floatingwindows.adapter.base.BaseViewHolder
import com.elnemr.floatingwindows.adapter.base.OnItemClickInterface
import com.elnemr.floatingwindows.databinding.ItemNoteLayoutBinding
import com.elnemr.floatingwindows.db.NoteEntity


class NoteViewHolder(private val binding: ItemNoteLayoutBinding) :
    BaseViewHolder<NoteEntity>(binding) {

    override fun bind(result: NoteEntity, action: OnItemClickInterface) {
        binding.tvNote.text = "${result.note}"
        binding.delete.setOnClickListener {
            action.onDeleteClicked(result)
        }
    }
}