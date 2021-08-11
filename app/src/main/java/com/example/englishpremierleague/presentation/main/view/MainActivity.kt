package com.example.englishpremierleague.presentation.main.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.englishpremierleague.R
import com.example.englishpremierleague.core.extension.*
import com.example.englishpremierleague.domain.model.local.Match
import com.example.englishpremierleague.presentation.main.adapter.MatchesAdapter
import com.example.englishpremierleague.presentation.main.intent.MainIntent
import com.example.englishpremierleague.presentation.main.util.FilterObject
import com.example.englishpremierleague.presentation.main.viewmodel.MainViewModel
import com.example.englishpremierleague.presentation.main.viewstate.MainState
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {
    private val mainViewModel: MainViewModel by viewModel()
    private lateinit var matchesAdapter: MatchesAdapter
    private val filterDataItem: FilterObject = FilterObject

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpUi()
        observeViewModel()
        setUpOnClickListeners()
    }

    private fun setUpUi() {
        matchesAdapter = MatchesAdapter(arrayListOf())
        recycler_view_matches.adapter = matchesAdapter

        val headersDecor = StickyRecyclerHeadersDecoration(matchesAdapter)
        recycler_view_matches.addItemDecoration(headersDecor)
        matchesAdapter.registerCustomAdapterDataObserver(headersDecor)

        matchesAdapter.setOnFavClicked {
            val match = Match(
                matchId = it.id, homeTeam = it.homeName, awayTeam = it.awayName,
                status = it.status, score = it.scoreStr, utcDate = it.utcDate
            )
            if (!it.isFav) {
                mainViewModel.favMatch(match)
            } else {
                mainViewModel.unFavMatch(match)
            }
            it.isFav = !it.isFav
            mainViewModel.filterData(filterDataItem)
        }

        runBlocking {
            lifecycleScope.launch {
                mainViewModel.mainIntent.send(MainIntent.FetchFavMatches)
            }
            lifecycleScope.launch {
                mainViewModel.mainIntent.send(MainIntent.FetchMatches)
            }
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            mainViewModel.state.collect {
                when (it) {
                    is MainState.Idle -> {

                    }
                    is MainState.Loading -> {
                        progressBar.visibility = View.VISIBLE
                    }
                    is MainState.Success -> {
                        progressBar.visibility = View.GONE
                        matchesAdapter.setItems(it.matches)
                    }
                    is MainState.Error -> {
                        progressBar.visibility = View.GONE
                        Toast.makeText(this@MainActivity, it.error, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun setUpOnClickListeners() {
        toggleClickListener()
        fromClickListener()
        toClickListener()
        statusClickListener()
    }

    private fun toggleClickListener() {
        iv_toggle.setOnClickListener {
            iv_toggle.isActivated = !iv_toggle.isActivated
            filterDataItem.fav = !filterDataItem.fav
            mainViewModel.filterData(filterDataItem)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun statusClickListener() {
        status.setOnClickListener {
            customAlert(this@MainActivity) {
                filterDataItem.status = it
                status_text.text = "${getString(R.string.status)}: ${filterDataItem.status}"
                mainViewModel.filterData(filterDataItem)
            }
        }
        status_delete.setOnClickListener {
            filterDataItem.status = getString(R.string.all)
            status_text.text = filterDataItem.status
            mainViewModel.filterData(filterDataItem)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun toClickListener() {
        to.setOnClickListener {
            customDialog(this@MainActivity) {
                to_text.text = "${getString(R.string.to)}: ${extractDate(it.time)}"
                it.set(
                    it.get(Calendar.YEAR),
                    it.get(Calendar.MONTH),
                    it.get(Calendar.DAY_OF_MONTH) + 1
                )
                filterDataItem.to = extractDateOnly(it.time)
                mainViewModel.filterData(filterDataItem)
            }
        }
        to_delete.setOnClickListener {
            filterDataItem.to = getString(R.string.to)
            to_text.text = filterDataItem.to
            mainViewModel.filterData(filterDataItem)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun fromClickListener() {
        from.setOnClickListener {
            customDialog(this@MainActivity) {
                from_text.text = "${getString(R.string.from)}: ${extractDate(it.time)}"
                it.set(
                    it.get(Calendar.YEAR),
                    it.get(Calendar.MONTH),
                    it.get(Calendar.DAY_OF_MONTH) - 1
                )
                filterDataItem.from = extractDateOnly(it.time)
                mainViewModel.filterData(filterDataItem)
            }
        }
        from_delete.setOnClickListener {
            filterDataItem.from = getString(R.string.from)
            from_text.text = filterDataItem.from
            mainViewModel.filterData(filterDataItem)
        }
    }
}