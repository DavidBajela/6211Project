package com.david.bajela.a6211project.Data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    var firstname: String,
    var lastname: String,
    @PrimaryKey
    val email: String,
    var password: String
)