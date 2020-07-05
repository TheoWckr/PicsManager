package com.example.myapplication.compressor

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.core.graphics.get
import androidx.core.graphics.set

object Compressor{

    tailrec fun runLengthEncoding(text:ByteArray,prev:String=""):String {
        if (text.isEmpty()){
            return prev
        }
        val initialChar = text.get(0)
        val count = text.takeWhile{ it==initialChar }.count()
        return runLengthEncoding(text.drop(count).toByteArray(),prev + "$count/$initialChar" )
    }


    fun divideSize (entry : Bitmap) : Bitmap {
        val newImage = Bitmap.createBitmap( entry.width/4, entry.height/4,Bitmap.Config.ARGB_8888);
        for (x in 0..(entry.width/4)-1)
            for(y in 0..(entry.height/4)-1)
                newImage[x,y] = entry[x*4, y*4];
        return newImage
    }

}