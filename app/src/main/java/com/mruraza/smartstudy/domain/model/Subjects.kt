package com.mruraza.smartstudy.domain.model

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Subjects(
    val name: String,
    val goalHours:Float,
    val colors:List<Int>,
    @PrimaryKey(autoGenerate = true)
    val subjectId:Int?=null
){
    companion object{
        val subjectCardColors =listOf(
            listOf(Color.Cyan, Color.Magenta, Color.LightGray, Color.DarkGray),
            listOf(Color.Red, Color.Green, Color.Yellow, Color.DarkGray),
            listOf(Color.Yellow, Color.Green, Color.LightGray, Color.Gray),
            listOf(Color.Magenta, Color.Red, Color.LightGray, Color.DarkGray),
            listOf(Color.Red, Color.Yellow, Color.LightGray, Color.Gray),

        )

    }

}