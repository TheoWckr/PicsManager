package com.example.myapplication.service

import android.graphics.Bitmap
import android.util.Log
import androidx.constraintlayout.widget.Constraints
import com.example.myapplication.helpers.auth
import com.example.myapplication.helpers.db
import com.example.myapplication.uploader.Uploader

object PhotoService {

    fun createPhoto(photoPath: String, name: String, album: String){

        val photo = hashMapOf(
            "photoPath" to photoPath,
            "name" to name,
            "owner" to auth.currentUser?.uid,
            "album" to album
        )
        // Add a new document with a generated ID
        db.collection("photo")
            .add(photo)
            .addOnSuccessListener { documentReference ->
                AlbumService.refreshUserAlbums()
            }
            .addOnFailureListener { e ->
                Log.w(Constraints.TAG, "Error adding document", e)
            }
    }
}