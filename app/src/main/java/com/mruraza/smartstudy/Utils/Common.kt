package com.mruraza.smartstudy.Utils

import androidx.compose.ui.graphics.Color
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter


enum class Priority(val title: String, val color: Color, val value: Int){
    HIGH("High", Color.Red, 2),
    MEDIUM("Medium", Color.Yellow, 1),
    LOW("Low", Color.Green, 0);



    companion object {
        fun fromInt(value: Int) = entries.firstOrNull() { it.value == value }?:MEDIUM
    }

}

fun Long?.changeMillisToString(): String {
    val date:LocalDate = this?.let {
        Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault()).toLocalDate()

    }?:LocalDate.now()
    return date.format(DateTimeFormatter.ofPattern("dd MMM yyyy"))
}