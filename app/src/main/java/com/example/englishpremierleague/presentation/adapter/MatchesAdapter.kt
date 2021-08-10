package com.example.englishpremierleague.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.englishpremierleague.databinding.HeaderItemBinding
import com.example.englishpremierleague.databinding.MatchItemBinding
import com.example.englishpremierleague.presentation.model.MatchDataItem
import com.example.englishpremierleague.utils.extractDateOnly
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter

class MatchesAdapter(private var matchesList: List<MatchDataItem>) : RecyclerView.Adapter<MatchesViewHolder>(),
    StickyRecyclerHeadersAdapter<HeaderViewHolder> {
    private lateinit var onFavClicked: (MatchDataItem) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchesViewHolder {
        val binding = MatchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MatchesViewHolder(binding, onFavClicked)
    }

    override fun getItemCount() = matchesList.size

    override fun onBindViewHolder(holder: MatchesViewHolder, position: Int) {
        holder.bind(matchesList[position])
    }

    fun setOnFavClicked(onFavClicked: (MatchDataItem) -> Unit) {
        this.onFavClicked = onFavClicked
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(_matchesList: List<MatchDataItem>) {
        matchesList = _matchesList
        notifyDataSetChanged()
    }

    override fun getHeaderId(position: Int): Long {
        return matchesList[position].headerId!!
    }

    override fun onCreateHeaderViewHolder(parent: ViewGroup): HeaderViewHolder {
        val binding = HeaderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HeaderViewHolder(binding)
    }

    override fun onBindHeaderViewHolder(holder: HeaderViewHolder, p1: Int) {
        holder.bind(matchesList[p1].formatDate)
    }
}