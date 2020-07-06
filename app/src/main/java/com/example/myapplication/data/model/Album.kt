package com.example.myapplication.data.model

import java.util.ArrayList

data class Album(
    val id: String,
    val name: String,
    val photos: ArrayList<String>?,
    val readers: List<String>?
)