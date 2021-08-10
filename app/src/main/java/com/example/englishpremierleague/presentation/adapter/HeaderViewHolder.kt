package com.example.englishpremierleague.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.englishpremierleague.databinding.HeaderItemBinding

class HeaderViewHolder(
    private val view: HeaderItemBinding
) : RecyclerView.ViewHolder(view.root) {
    fun bind(title: String) {
        view.headerTitle.text = title
    }
}