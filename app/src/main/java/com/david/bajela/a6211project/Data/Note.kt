package com.david.bajela.a6211project.Data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    @PrimaryKey(autoGenerate=true)
    val id: Int?,
    var Owner: String,
    var title: String,
    var note: String,
    var iconName: Int,
    //var audiomessage: AudioRecord?,
    var image: String?,
    var lastUpdate: Long
)