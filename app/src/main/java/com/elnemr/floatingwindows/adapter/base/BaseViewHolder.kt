package com.elnemr.floatingwindows.adapter.base

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseViewHolder<in T>(private val binding: ViewBinding) :
    RecyclerView.ViewHolder(binding.root) {
    protected val context = binding.root.context
    protected val resources = binding.root.resources
    abstract fun bind(result: T, action: OnItemClickInterface)
}