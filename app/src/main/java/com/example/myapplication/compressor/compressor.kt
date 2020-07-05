package com.example.myapplication.compressor

object Compressor{

    tailrec fun runLengthEncoding(text:ByteArray,prev:String=""):String {
        if (text.isEmpty()){
            return prev
        }
        val initialChar = text.get(0)
        val count = text.takeWhile{ it==initialChar }.count()
        return runLengthEncoding(text.drop(count).toByteArray(),prev + "$count/$initialChar" )
    }

}