package com.example.englishpremierleague.presentation.main.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.englishpremierleague.databinding.HeaderItemBinding
import com.example.englishpremierleague.domain.model.local.Match
import com.example.englishpremierleague.presentation.main.model.MatchDataItem

class HeaderViewHolder(
    private val view: HeaderItemBinding
) : RecyclerView.ViewHolder(view.root) {
    fun bind(title: String) {
        view.headerTitle.text = title
    }
}