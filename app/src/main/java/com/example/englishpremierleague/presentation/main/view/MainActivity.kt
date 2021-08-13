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
            mainViewModel.match = Match(
                matchId = it.id, homeTeam = it.homeName, awayTeam = it.awayName,
                status = it.status, score = it.scoreStr, utcDate = it.utcDate
            )
            if (!it.isFav) {
                lifecycleScope.launch { mainViewModel.mainIntent.send(MainIntent.FavMatch) }
            } else {
                lifecycleScope.launch { mainViewModel.mainIntent.send(MainIntent.UnFavFavMatch) }
            }
            it.isFav = !it.isFav
            lifecycleScope.launch { mainViewModel.mainIntent.send(MainIntent.FilterMatches) }
        }

        runBlocking {
            lifecycleScope.launch { mainViewModel.mainIntent.send(MainIntent.FetchFavMatches) }
            lifecycleScope.launch { mainViewModel.mainIntent.send(MainIntent.FetchMatches) }
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
            mainViewModel.filterObject.fav = !mainViewModel.filterObject.fav
            lifecycleScope.launch { mainViewModel.mainIntent.send(MainIntent.FilterMatches) }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun statusClickListener() {
        status.setOnClickListener {
            this@MainActivity.customAlert {
                mainViewModel.filterObject.status = it
                status_text.text =
                    "${getString(R.string.status)}: ${mainViewModel.filterObject.status}"
                lifecycleScope.launch { mainViewModel.mainIntent.send(MainIntent.FilterMatches) }
            }
        }
        status_delete.setOnClickListener {
            mainViewModel.filterObject.status = getString(R.string.all)
            status_text.text = mainViewModel.filterObject.status
            lifecycleScope.launch { mainViewModel.mainIntent.send(MainIntent.FilterMatches) }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun toClickListener() {
        to.setOnClickListener {
            this@MainActivity.customDialog {
                to_text.text = "${getString(R.string.to)}: ${it.time.extractDate()}"
                it.set(
                    it.get(Calendar.YEAR),
                    it.get(Calendar.MONTH),
                    it.get(Calendar.DAY_OF_MONTH) + 1
                )
                mainViewModel.filterObject.to = it.time.extractDateOnly()
                lifecycleScope.launch { mainViewModel.mainIntent.send(MainIntent.FilterMatches) }
            }
        }
        to_delete.setOnClickListener {
            mainViewModel.filterObject.to = getString(R.string.to)
            to_text.text = mainViewModel.filterObject.to
            lifecycleScope.launch { mainViewModel.mainIntent.send(MainIntent.FilterMatches) }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun fromClickListener() {
        from.setOnClickListener {
            this@MainActivity.customDialog {
                from_text.text = "${getString(R.string.from)}: ${it.time.extractDate()}"
                it.set(
                    it.get(Calendar.YEAR),
                    it.get(Calendar.MONTH),
                    it.get(Calendar.DAY_OF_MONTH) - 1
                )
                mainViewModel.filterObject.from = it.time.extractDateOnly()
                lifecycleScope.launch { mainViewModel.mainIntent.send(MainIntent.FilterMatches) }
            }
        }
        from_delete.setOnClickListener {
            mainViewModel.filterObject.from = getString(R.string.from)
            from_text.text = mainViewModel.filterObject.from
            lifecycleScope.launch { mainViewModel.mainIntent.send(MainIntent.FilterMatches) }
        }
    }
}