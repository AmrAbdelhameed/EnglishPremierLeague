package com.example.englishpremierleague.presentation.main.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.englishpremierleague.core.util.Constants.MatchTypes.ITEM
import com.example.englishpremierleague.core.util.Constants.MatchTypes.LIST
import com.example.englishpremierleague.databinding.HeaderItemBinding
import com.example.englishpremierleague.databinding.MatchItemBinding
import com.example.englishpremierleague.databinding.MatchesHorizontalItemBinding
import com.example.englishpremierleague.presentation.main.model.MatchDataItem
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter
import kotlin.time.ExperimentalTime

class MatchesAdapter(private var matchesList: List<MatchDataItem>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    StickyRecyclerHeadersAdapter<HeaderViewHolder> {
    private lateinit var onFavClicked: (MatchDataItem) -> Unit

    override fun getItemViewType(position: Int): Int {
        return if(matchesList[position].matches.isNotEmpty()) LIST else ITEM
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == LIST) {
            val binding = MatchesHorizontalItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            MatchesHorizontalViewHolder(binding, onFavClicked)
        } else {
            val binding = MatchItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false)
            MatchesViewHolder(binding, onFavClicked)
        }
    }

    override fun getItemCount() = matchesList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == LIST)
            (holder as MatchesHorizontalViewHolder).bind(matchesList[position].matches)
        else
            (holder as MatchesViewHolder).bind(matchesList[position])
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
        return matchesList[position].headerId
    }

    override fun onCreateHeaderViewHolder(parent: ViewGroup): HeaderViewHolder {
        val binding = HeaderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HeaderViewHolder(binding)
    }

    @ExperimentalTime
    override fun onBindHeaderViewHolder(holder: HeaderViewHolder, p1: Int) {
        holder.bind(matchesList[p1].formatDate)
    }
}