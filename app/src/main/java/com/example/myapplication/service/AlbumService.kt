package com.example.myapplication.service

import android.util.Log
import androidx.constraintlayout.widget.Constraints.TAG

import com.example.myapplication.data.model.Album
import com.example.myapplication.helpers.auth
import com.example.myapplication.helpers.db
import com.google.firebase.firestore.FieldValue
import kotlinx.coroutines.android.awaitFrame
import java.util.*
import kotlin.collections.HashMap

object AlbumService {
    var albumList = ArrayList<Album>()

     fun refreshUserAlbums() {
        getUserAlbums(albumList)
    }
        fun albumCreate(name: String){
        val album = hashMapOf(
            "name" to name,
            "owner" to auth.currentUser?.uid
        )
            db.collection("user").get()
            // Add a new document with a generated ID
            db.collection("album")
                .add(album)
                .addOnSuccessListener { documentReference ->
                    refreshUserAlbums()
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error adding document", e)
                }
        }


    fun getUserAlbums( receiver :ArrayList<Album>){
        db.collection("album")
            .whereEqualTo("owner", auth.currentUser?.uid)
            .get()
            .addOnSuccessListener { documents ->

                var newAlbumList = ArrayList<Album>()
                for (document in documents) {
                    var photos = arrayOf<String>()
                    var readers = arrayListOf<String>()
                    var album = Album(document.id, document.data["name"] as String, photos,readers)
                    newAlbumList.add(album)

                    document.data.keys
                    //receiver.putAll(document.data)
                }
                receiver.clear()
                receiver.addAll(newAlbumList)
                PhotoService.populatePhoto()
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
        val albumToDelete = db.collection("album").document(idAlbum).delete()
        refreshUserAlbums()
    }

    fun albumRename(newName : String, idAlbum : String ) {
        val albumUpdated = db.collection("album").document(idAlbum)
        albumUpdated.update("name", newName)
        refreshUserAlbums()

    }

    fun addReader(idReader : String, idAlbum : String ){
        val albumUpdated = db.collection("album").document(idAlbum)
        albumUpdated.update("readers", FieldValue.arrayUnion(idReader))
        refreshUserAlbums()

    }

    fun removeReader(idReader : String, idAlbum : String ){
        val albumUpdated = db.collection("album").document(idAlbum)
        albumUpdated.update("readers", FieldValue.arrayRemove(idReader))
        refreshUserAlbums()

    }

    fun addPicture(idPicture: String, idAlbum: String ){
        val albumUpdated = db.collection("album").document(idAlbum)
        albumUpdated.update("photos", FieldValue.arrayUnion(idPicture))
        refreshUserAlbums()

    }


    fun movePicture(idPicture: String, idCurrentAlbum: String, idNewAlbum : String ){
        val currentAlbumUpdated = db.collection("album").document(idCurrentAlbum)
        currentAlbumUpdated.update("photos", FieldValue.arrayRemove(idPicture))
        val newAlbumUpdated = db.collection("album").document(idNewAlbum)
        newAlbumUpdated.update("photos", FieldValue.arrayUnion(idPicture))
        refreshUserAlbums()
    }

    fun getAlbumFromAlbumName(albumName : String): Album{
        return albumList.filter { album -> album.name == albumName }.first()
    }

    fun getAlbumFromId(albumId : String): Album{
        return albumList.filter { album -> album.id == albumId }.first()
    }

    fun getIdFromAlbumName(albumName : String): String{
        return getAlbumFromAlbumName(albumName).id
    }


}