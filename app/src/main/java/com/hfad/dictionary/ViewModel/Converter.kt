package com.hfad.dictionary.ViewModel

import androidx.room.TypeConverter
import com.hfad.dictionary.models.card.Status

class Converter {

    @TypeConverter
    fun fromStatus(status: Status): String {
        return status.name
    }

    @TypeConverter
    fun toStatus(status: String): Status {
        return Status.valueOf(status)
    }
}