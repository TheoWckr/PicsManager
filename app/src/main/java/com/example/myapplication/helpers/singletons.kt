package com.example.myapplication.helpers

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

public val auth : FirebaseAuth = FirebaseAuth.getInstance()
public val storage = Firebase.storage
public val db = Firebase.firestore
