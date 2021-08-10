package com.example.englishpremierleague.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.englishpremierleague.databinding.MatchItemBinding
import com.example.englishpremierleague.presentation.model.MatchDataItem

class MatchesViewHolder(
    private val view: MatchItemBinding,
    private var onFavClicked: (MatchDataItem) -> Unit
) : RecyclerView.ViewHolder(view.root) {
    fun bind(item: MatchDataItem) {
        view.status.text = item.status
        view.homeName.text = item.homeName
        view.awayName.text = item.awayName
        view.scoreOrTime.text = item.scoreOrTime
        view.ivToggle.isActivated = item.isFav
        view.ivToggle.setOnClickListener {
            view.ivToggle.isActivated = !view.ivToggle.isActivated
            onFavClicked.invoke(item)
        }
    }
}