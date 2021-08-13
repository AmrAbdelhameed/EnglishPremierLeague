package com.example.englishpremierleague.presentation.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.englishpremierleague.databinding.MatchItemBinding
import com.example.englishpremierleague.presentation.main.model.MatchDataItem

class MatchesHorizontalAdapter(private var matchesList: List<MatchDataItem>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var onFavClicked: (MatchDataItem) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = MatchItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MatchesViewHolder(binding, onFavClicked)
    }

    override fun getItemCount() = matchesList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MatchesViewHolder).bind(matchesList[position])
    }

    fun setOnFavClicked(onFavClicked: (MatchDataItem) -> Unit) {
        this.onFavClicked = onFavClicked
    }
}