package com.example.englishpremierleague.presentation.main.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.englishpremierleague.databinding.MatchesHorizontalItemBinding
import com.example.englishpremierleague.presentation.main.model.MatchDataItem

class MatchesHorizontalViewHolder(
    private val view: MatchesHorizontalItemBinding,
    private var onFavClicked: (MatchDataItem) -> Unit
) : RecyclerView.ViewHolder(view.root) {
    fun bind(items: List<MatchDataItem>) {
       val matchesAdapter = MatchesHorizontalAdapter(items)
        view.horizontalMatches.adapter = matchesAdapter
        matchesAdapter.setOnFavClicked {
            onFavClicked.invoke(it)
        }
    }
}