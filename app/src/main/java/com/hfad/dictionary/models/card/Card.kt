package com.hfad.dictionary.models.card

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "card_table")
@Parcelize
data class Card (
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val word:String,
    val status: Status,
    val definition:String,
    val example:String
        ):Parcelable

