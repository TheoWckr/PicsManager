package com.example.myapplication.uploader

import android.graphics.Bitmap
import com.example.myapplication.service.AlbumService
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream

object Uploader {
    val storage = Firebase.storage
    // Create a storage reference from our app
    val storageRef = storage.reference

    fun upload(bitmap : Bitmap, name: String){
       val listRef =  storage.reference.root;
        listRef.listAll()
            .addOnSuccessListener { listResult ->
                listResult.prefixes.forEach { prefix ->
                    // All the prefixes under listRef.
                    // You may call listAll() recursively on them.
                }

                listResult.items.forEach { item ->
                    // All the items under listRef.
                }
            }
            .addOnFailureListener {
               it.message
            }
        var spaceRef = storageRef.child(AlbumService.currentAlbum+" /" + name)
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        var uploadTask = spaceRef.putBytes(data)
        uploadTask.addOnFailureListener {
            // Handle unsuccessful uploads
        }.addOnSuccessListener {
            // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
            // ...
        }

    }
}