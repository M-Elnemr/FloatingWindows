package com.elnemr.floatingwindows.adapter.run

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import com.elnemr.floatingwindows.adapter.base.BaseAdapter
import com.elnemr.floatingwindows.adapter.base.BaseViewHolder
import com.elnemr.floatingwindows.adapter.base.OnItemClickInterface
import com.elnemr.floatingwindows.databinding.ItemNoteLayoutBinding
import com.elnemr.floatingwindows.db.NoteEntity

class NoteAdapter(private val onItemClickInterface: OnItemClickInterface) :
    BaseAdapter<NoteEntity>() {

    private val mDiffer = AsyncListDiffer(this, NoteDiffCallBack)

    override fun setDataList(dataList: List<NoteEntity>) {
        mDiffer.submitList(dataList)
    }

    override fun addDataList(dataList: List<NoteEntity>) {
        mDiffer.currentList.addAll(dataList)
    }

    override fun clearDataList() {
        mDiffer.currentList.clear()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<NoteEntity> =
        NoteViewHolder(
            ItemNoteLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )


    override fun onBindViewHolder(holder: BaseViewHolder<NoteEntity>, position: Int) =
        holder.bind(mDiffer.currentList[position], onItemClickInterface)

    override fun getItemCount(): Int = mDiffer.currentList.size


}