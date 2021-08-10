package com.example.englishpremierleague.utils

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

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

fun compareDates(date: Date): Int {
    val firstCalendar = Calendar.getInstance()
    firstCalendar.time = date
    return firstCalendar.get(Calendar.DAY_OF_MONTH) - Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
}