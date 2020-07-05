package com.example.myapplication.service

import android.util.Log
import androidx.constraintlayout.widget.Constraints.TAG
import com.example.myapplication.data.LoginRepository
import com.example.myapplication.helpers.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.util.*
import kotlin.collections.HashMap

object AlbumService {

    var currentAlbum = "default";

    val db = Firebase.firestore
    val storage = Firebase.storage

    fun albumCreate(name: String){
        val album = hashMapOf(
            "name" to name,
            "owner" to auth.currentUser?.uid
        )
        // Add a new document with a generated ID
        db.collection("album")
            .add(album)
            .addOnSuccessListener { documentReference ->
                currentAlbum = documentReference.id;

                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }

    fun getUserAlbums(receiver : HashMap<String, Any>){
        db.collection("album")
                //TODO replace with getUser()
            .whereEqualTo("owner", auth.currentUser?.uid)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    receiver.putAll(document.data)
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }
    }

    fun getOthersAlbums(receiver : HashMap<String, Any>){
        auth.currentUser?.uid?.let {
            db.collection("album")
                .whereArrayContains("readers", it)
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        receiver.putAll(document.data)
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting documents: ", exception)
                }
        }
    }

    fun albumDelete(idAlbum : String){
        val albumToDelete = db.collection("album").document(idAlbum)
    }

    fun albumRename(newName : String, idAlbum : String ) {
        val albumUpdated = db.collection("album").document(idAlbum)
        albumUpdated.update("name", newName)
    }

    fun addReader(idReader : String, idAlbum : String ){
        val albumUpdated = db.collection("album").document(idAlbum)
        albumUpdated.update("readers", FieldValue.arrayUnion(idReader))
    }

    fun removeReader(idReader : String, idAlbum : String ){
        val albumUpdated = db.collection("album").document(idAlbum)
        albumUpdated.update("readers", FieldValue.arrayRemove(idReader))
    }

    fun addPicture(idPicture: String, idAlbum: String ){
        val albumUpdated = db.collection("album").document(idAlbum)
        albumUpdated.update("photos", FieldValue.arrayUnion(idPicture))
    }


    fun movePicture(idPicture: String, idCurrentAlbum: String, idNewAlbum : String ){
        val currentAlbumUpdated = db.collection("album").document(idCurrentAlbum)
        currentAlbumUpdated.update("photos", FieldValue.arrayRemove(idPicture))
        val newAlbumUpdated = db.collection("album").document(idNewAlbum)
        newAlbumUpdated.update("photos", FieldValue.arrayUnion(idPicture))
    }
}