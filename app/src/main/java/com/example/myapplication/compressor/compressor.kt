package com.example.myapplication.compressor

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.core.graphics.get
import androidx.core.graphics.red
import androidx.core.graphics.set
import androidx.core.graphics.toColor
import java.io.ByteArrayOutputStream
import java.nio.Buffer
import java.nio.ByteBuffer
import java.nio.MappedByteBuffer

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
        val divisor = 7;
        val newImage = Bitmap.createBitmap( entry.width/divisor, entry.height/divisor,Bitmap.Config.RGB_565);

        for (x in 0..(entry.width/divisor)-1)
            for(y in 0..(entry.height/divisor)-1)
                newImage[x,y] = entry[x*divisor, y*divisor];
        entry.byteCount
        newImage.byteCount
        return newImage
    }

    fun divideSizeAverage (entry : Bitmap) : Bitmap {
        val divisor = 7;
        val newImage = Bitmap.createBitmap( entry.width/divisor, entry.height/divisor,Bitmap.Config.ARGB_8888);

        for (x in 0..(entry.width/divisor)-1)
            for(y in 0..(entry.height/divisor)-1)

                newImage[x,y] = (entry[x*divisor, y*divisor] + entry[(x*divisor)+divisor/2, (y*divisor)+divisor/2] + entry[(x*divisor)+divisor-1, (y*divisor)+divisor-1])/3
                        entry.byteCount
        newImage.byteCount
        return newImage
    }

    fun divideSizeWithArray (entry : Bitmap) : Bitmap {
        val divisor = 10;
        val newWidth = entry.width/divisor
        val newHeight = entry.height/divisor
         Bitmap.createBitmap( newWidth, newHeight,Bitmap.Config.ARGB_8888);

        val baos = ByteArrayOutputStream()
        val buffer = MappedByteBuffer.allocate(entry.allocationByteCount)
        entry.copyPixelsToBuffer(buffer);

        val newImagebuffer = MappedByteBuffer.allocate(newWidth*newHeight*4)


        for (x in 0..(entry.width/divisor)-1)
            for(y in 0..(entry.height/divisor)-1)
                for(i in 0..3 )
                newImagebuffer.put(buffer[((x*divisor)*(y*divisor)*4)+i])

        val newImage = BitmapFactory.decodeByteArray(newImagebuffer.array(),0,newWidth*newHeight*4 );
        return newImage
    }


}