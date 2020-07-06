package com.example.myapplication.uploader

import android.graphics.Bitmap
import com.example.myapplication.helpers.auth
import com.example.myapplication.helpers.storage
import com.example.myapplication.service.PhotoService.createPhoto
import java.io.ByteArrayOutputStream
import kotlin.reflect.KFunction0

object Uploader {
    // Create a storage reference from our app
    val storageRef = storage.reference

    fun upload(bitmap: Bitmap, name: String, validation: KFunction0<Unit>, album : String){
        //Creation d'un nom pour le storage
        var spaceRef = storageRef.child(auth.uid+" /" + name)
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)


        var uploadTask = spaceRef.putBytes(baos.toByteArray())
        uploadTask.addOnFailureListener {
            // Handle unsuccessful uploads
        }.addOnSuccessListener {
            createPhoto(spaceRef.path, name, album)
            validation
        }
    }
}