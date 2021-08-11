package com.example.englishpremierleague.core.extension

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.englishpremierleague.R
import com.example.englishpremierleague.core.util.Constants
import com.example.englishpremierleague.presentation.main.adapter.MatchesAdapter
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime
import kotlin.time.milliseconds

fun TextView.selectDate(context: Context, str: String) {
    val calendar: Calendar = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            val secondCalendar = Calendar.getInstance()
            secondCalendar.set(year, month, dayOfMonth)
            this.text = "$str: ${extractDate(secondCalendar.time)}"
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )
    datePickerDialog.show()
}

fun TextView.selectStatus(context: Context, str: String) {
    val array = arrayOf(
        Constants.MatchStatus.SCHEDULED,
        Constants.MatchStatus.HALF_TIME,
        Constants.MatchStatus.FULL_TIME,
        Constants.MatchStatus.EXTRA_TIME,
        Constants.MatchStatus.PENALTIES
    )
    val builder = AlertDialog.Builder(context)
    builder.setTitle("Choose $str.")
    builder.setItems(array) { _, pos ->
        this.text = "$str: ${array[pos]}"
    }
    val dialog = builder.create()
    dialog.show()
}

@SuppressLint("SimpleDateFormat")
fun convertDate(utcDate: String): Date {
    return SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(utcDate)
}

@SuppressLint("SimpleDateFormat")
fun convertDateOnly(utcDate: String): Date? {
    return SimpleDateFormat("yyyy-MM-dd").parse(utcDate)
}

@SuppressLint("SimpleDateFormat")
fun extractDateOnly(utcDate: Date): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd")
    return formatter.format(utcDate)
}

@SuppressLint("SimpleDateFormat")
fun extractDate(utcDate: Date): String {
    val formatter = SimpleDateFormat("EEE, d MMM")
    return formatter.format(utcDate)
}

@SuppressLint("SimpleDateFormat")
fun extractDateOnly(utcDate: String): String {
    val formatter = SimpleDateFormat("EEE, d MMM")
    return formatter.format(convertDate(utcDate))
}

@SuppressLint("SimpleDateFormat")
fun extractTimeOnly(utcDate: String): String {
    val formatter = SimpleDateFormat("hh:mma")
    return formatter.format(convertDate(utcDate))
}

fun getCurrentDateTime(): Date {
    return Calendar.getInstance().time
}

fun differenceBetweenDates(firstDate: Date, secondDate: Date): Long {
    val millionSeconds = firstDate.time - secondDate.time
    return TimeUnit.MILLISECONDS.toDays(millionSeconds)
}

@ExperimentalTime
fun compareDates(date: Date): Int {
    val firstCalendar = Calendar.getInstance()
    firstCalendar.time = date

    val secondCalendar = Calendar.getInstance()

    return if (firstCalendar.get(Calendar.YEAR) == secondCalendar.get(Calendar.YEAR) &&
        firstCalendar.get(Calendar.MONTH) == secondCalendar.get(Calendar.MONTH)
    ) {
        firstCalendar.get(Calendar.DAY_OF_MONTH) - secondCalendar.get(Calendar.DAY_OF_MONTH)
    } else {
        val diffInMillis: Long = firstCalendar.timeInMillis - secondCalendar.timeInMillis
        return diffInMillis.milliseconds.toInt(DurationUnit.DAYS)
    }
}

fun MatchesAdapter.registerCustomAdapterDataObserver(headersDecor: StickyRecyclerHeadersDecoration) {
    this.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
        override fun onChanged() {
            headersDecor.invalidateHeaders()
        }
    })
}

fun customDialog(context: Context, selected: (Calendar) -> Unit) {
    val calendar: Calendar = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            val secondCalendar = Calendar.getInstance()
            secondCalendar.set(year, month, dayOfMonth)
            selected.invoke(secondCalendar)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )
    datePickerDialog.show()
}

fun customAlert(context: Context, selected: (String) -> Unit) {
    val array = arrayOf(
        Constants.MatchStatus.SCHEDULED,
        Constants.MatchStatus.HALF_TIME,
        Constants.MatchStatus.FULL_TIME,
        Constants.MatchStatus.EXTRA_TIME,
        Constants.MatchStatus.PENALTIES
    )
    val builder = AlertDialog.Builder(context)
    builder.setTitle(context.getString(R.string.choose_status))
    builder.setItems(array) { _, pos ->
        selected.invoke(array[pos])
    }
    val dialog = builder.create()
    dialog.show()
}