package com.example.myapplication.data.model

data class Album(
    val id : String,
    val name: String,
    val photos : HashMap<String, String>,
    val readers : List<String>
)