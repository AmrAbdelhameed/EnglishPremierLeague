package com.example.englishpremierleague.presentation.view

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
import com.example.englishpremierleague.R
import com.example.englishpremierleague.domain.entity.local.Match
import com.example.englishpremierleague.presentation.adapter.MatchesAdapter
import com.example.englishpremierleague.presentation.intent.MainIntent
import com.example.englishpremierleague.presentation.utils.FilterObject
import com.example.englishpremierleague.presentation.viewmodel.MainViewModel
import com.example.englishpremierleague.presentation.viewstate.MainState
import com.example.englishpremierleague.utils.*
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
        matchesAdapter.registerAdapterDataObserver(object : AdapterDataObserver() {
            override fun onChanged() {
                headersDecor.invalidateHeaders()
            }
        })
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
            val array = arrayOf(
                Constants.MatchStatus.SCHEDULED,
                Constants.MatchStatus.HALF_TIME,
                Constants.MatchStatus.FULL_TIME,
                Constants.MatchStatus.EXTRA_TIME,
                Constants.MatchStatus.PENALTIES
            )
            val builder = AlertDialog.Builder(this@MainActivity)
            builder.setTitle(getString(R.string.choose_status))
            builder.setItems(array) { _, pos ->
                filterDataItem.status = array[pos]
                input3.text = "${getString(R.string.status)}: ${filterDataItem.status}"
                mainViewModel.filterData(filterDataItem)
            }
            val dialog = builder.create()
            dialog.show()
        }
        delete3.setOnClickListener {
            filterDataItem.status = getString(R.string.all)
            input3.text = filterDataItem.status
            mainViewModel.filterData(filterDataItem)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun toClickListener() {
        to.setOnClickListener {
            val calendar: Calendar = Calendar.getInstance()
            val datePickerDialog = DatePickerDialog(
                this@MainActivity,
                { _, year, month, dayOfMonth ->
                    val secondCalendar = Calendar.getInstance()
                    secondCalendar.set(year, month, dayOfMonth)
                    input2.text = "${getString(R.string.to)}: ${extractDate(secondCalendar.time)}"
                    secondCalendar.set(year, month, dayOfMonth + 1)
                    filterDataItem.to = extractDateOnly(secondCalendar.time)
                    mainViewModel.filterData(filterDataItem)
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show()
        }
        delete2.setOnClickListener {
            filterDataItem.to = getString(R.string.to)
            input2.text = filterDataItem.to
            mainViewModel.filterData(filterDataItem)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun fromClickListener() {
        from.setOnClickListener {
            val calendar: Calendar = Calendar.getInstance()
            val datePickerDialog = DatePickerDialog(this@MainActivity,
                { _, year, month, dayOfMonth ->
                    val secondCalendar = Calendar.getInstance()
                    secondCalendar.set(year, month, dayOfMonth)
                    input1.text = "${getString(R.string.from)}: ${extractDate(secondCalendar.time)}"
                    secondCalendar.set(year, month, dayOfMonth - 1)
                    filterDataItem.from = extractDateOnly(secondCalendar.time)
                    mainViewModel.filterData(filterDataItem)
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show()
        }
        delete1.setOnClickListener {
            filterDataItem.from = getString(R.string.from)
            input1.text = filterDataItem.from
            mainViewModel.filterData(filterDataItem)
        }
    }
}