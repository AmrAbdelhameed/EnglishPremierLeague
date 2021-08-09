package com.example.englishpremierleague.presentation.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.englishpremierleague.R
import com.example.englishpremierleague.presentation.intent.MainIntent
import com.example.englishpremierleague.presentation.viewmodel.MainViewModel
import com.example.englishpremierleague.presentation.viewstate.MainState
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {
    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupUi()
        observeViewModel()
    }

    private fun setupUi() {
        lifecycleScope.launch {
            mainViewModel.mainIntent.send(MainIntent.FetchMatches)
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
                        Log.d("TAG", "observeViewModel: ${it.matches}")
                    }
                    is MainState.Error -> {
                        progressBar.visibility = View.GONE
                        Toast.makeText(this@MainActivity, it.error, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}