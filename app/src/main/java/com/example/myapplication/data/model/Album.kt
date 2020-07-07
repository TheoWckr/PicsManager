package com.example.myapplication.data.model

import java.util.ArrayList

data class Album(
    val id: String,
    val name: String,
    var photos: Array<String>,
    var readers: List<String>?
)