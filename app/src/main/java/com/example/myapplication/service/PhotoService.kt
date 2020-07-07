package com.example.myapplication.service

import android.graphics.Bitmap
import android.util.Log
import androidx.constraintlayout.widget.Constraints
import com.example.myapplication.data.model.Album
import com.example.myapplication.helpers.auth
import com.example.myapplication.helpers.db
import com.example.myapplication.uploader.Uploader
import java.util.ArrayList

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

    fun populatePhoto(){

        db.collection("photo")
            .whereEqualTo("owner", auth.currentUser?.uid)
            .get()
            .addOnSuccessListener { documents ->
                val photoListe = arrayOf<String>()
                AlbumService.albumList
                for (document in documents) {
                    AlbumService.getAlbumFromId(document.data["album"] as String).photos =
                    AlbumService.getAlbumFromId(document.data["album"] as String).photos.plus(document.data["photoPath"] as String)
                }
            }
            .addOnFailureListener { exception ->
                Log.w(Constraints.TAG, "Error getting documents: ", exception)
            }

    }
}