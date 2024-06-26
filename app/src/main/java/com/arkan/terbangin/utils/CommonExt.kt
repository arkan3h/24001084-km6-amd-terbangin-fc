package com.arkan.terbangin.utils

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.AppCompatEditText
import androidx.fragment.app.Fragment
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.util.TimeZone

val Number.toPx
    get() =
        TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this.toFloat(),
            Resources.getSystem().displayMetrics,
        )

fun AppCompatEditText.doneEditing(doneBlock: () -> Unit) {
    this.setOnEditorActionListener { _, actionId, event ->
        if (actionId == EditorInfo.IME_ACTION_SEARCH ||
            actionId == EditorInfo.IME_ACTION_DONE ||
            event != null &&
            event.action == KeyEvent.ACTION_DOWN &&
            event.keyCode == KeyEvent.KEYCODE_ENTER
        ) {
            if (event == null || !event.isShiftPressed) {
                // the user is done typing.
                doneBlock.invoke()
                return@setOnEditorActionListener true
            }
        }
        return@setOnEditorActionListener true
    }
}

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun formatDateHourString(dateString: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale("id", "ID"))

    val outputFormat = SimpleDateFormat("HH.mm", Locale("id", "ID"))

    val date = inputFormat.parse(dateString)

    return date?.let { outputFormat.format(it) } ?: ""
}

fun formatDateString(dateString: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale("id", "ID"))

    val outputFormat = SimpleDateFormat("dd MMMM yyyy", Locale("id", "ID"))

    val date = inputFormat.parse(dateString)

    return date?.let { outputFormat.format(it) } ?: ""
}

fun formatDateNotification(dateString: String): String {
    val originalFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    originalFormat.timeZone = TimeZone.getTimeZone("UTC")

    val targetFormat = SimpleDateFormat("dd MMMM yyyy, HH:mm", Locale("id", "ID"))
    targetFormat.timeZone = TimeZone.getDefault()

    val date = originalFormat.parse(dateString)

    return date?.let { targetFormat.format(it) } ?: ""
}

fun stringToLocalDate(dateString: String): LocalDate {
    val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    val localDateTime = LocalDateTime.parse(dateString, dateTimeFormatter)
    return localDateTime.toLocalDate()
}

fun formatHours(minutes: Int): String {
    val hours = minutes / 60
    val remainingMinutes = minutes % 60
    return "$hours jam $remainingMinutes menit"
}

fun formatHoursEng(minutes: Int): String {
    val hours = minutes / 60
    val remainingMinutes = minutes % 60
    return "${hours}h ${remainingMinutes}m"
}

fun formatDateHourStringHistory(dateString: String): String {
    val originalFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    originalFormat.timeZone = TimeZone.getTimeZone("UTC")

    val targetFormat = SimpleDateFormat("HH.mm", Locale.getDefault())
    targetFormat.timeZone = TimeZone.getDefault()

    val date = originalFormat.parse(dateString)

    return date?.let { targetFormat.format(it) } ?: ""
}

fun formatDateStringHistory(dateString: String): String {
    val originalFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    originalFormat.timeZone = TimeZone.getTimeZone("UTC")

    val targetFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
    targetFormat.timeZone = TimeZone.getDefault()

    val date = originalFormat.parse(dateString)

    return date?.let { targetFormat.format(it) } ?: ""
}

fun formatMonthHeaderStringHistory(dateString: String): String {
    val originalFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    originalFormat.timeZone = TimeZone.getTimeZone("UTC")

    val targetFormat = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
    targetFormat.timeZone = TimeZone.getDefault()

    val date = originalFormat.parse(dateString)

    return date?.let { targetFormat.format(it) } ?: ""
}

fun formatClassHistory(input: String): String {
    return input.split('_')
        .joinToString(" ") { it.lowercase().replaceFirstChar { char -> char.uppercase() } }
}
