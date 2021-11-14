package com.launchpad.codechallenge03.models

import androidx.compose.ui.graphics.ImageBitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.*

@Entity
data class Animal(
    @PrimaryKey
    val id: String,
    @ColumnInfo
    val name: String,
    @ColumnInfo
    val description: String,
    @ColumnInfo
    val timeStamp: Long,
    @ColumnInfo
    val type: Type,
    @ColumnInfo
    val size: Size
){
    enum class Type{LAND, SEA, AIR}
    enum class Size{SMALL, MEDIUM, LARGE}

    fun getReadableTimeStamp(): String{
        val formatter = SimpleDateFormat("dd/MM/yyyy hh:mm:ss", Locale.getDefault())
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timeStamp
        return formatter.format(calendar.time)
    }
}
