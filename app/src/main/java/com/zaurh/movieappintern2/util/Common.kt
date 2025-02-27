package com.zaurh.movieappintern2.util

import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale

fun formatTimestamp(timestamp: Timestamp): String {
    val date = timestamp.toDate() // Convert Firebase Timestamp to Date
    val formatter = SimpleDateFormat("HH:mm • MMM dd, yyyy", Locale.getDefault())
    return formatter.format(date)
}