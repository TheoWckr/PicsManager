package com.example.myapplication


import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.myapplication.helpers.MetaI18n
import com.example.myapplication.helpers.auth
import com.example.myapplication.service.AlbumService
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.textfield.TextInputEditText
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    val REQUEST_IMAGE_CAPTURE = 1
    val REQUEST_TAKE_PHOTO = 1
    lateinit var currentPhotoPath: String
    lateinit var btnLogout : Button

    class I18n(
        override val activityTitle: String,
        val takePicture: String
    ) : MetaI18n

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

// // old logout button
//        btnLogout = findViewById(R.id.btnLogout)
//
//        btnLogout.setOnClickListener {
//            auth.signOut()
//            startActivity(Intent(this, LoginActivity::class.java))
//            Toast.makeText(baseContext, "You are signed out", Toast.LENGTH_LONG)
//            finish()
//        }

        println("this is the current user")

        println(auth.currentUser?.email)

        if (auth.currentUser != null) {
            val btnLoggedAs = findViewById<TextView>(R.id.loggedAs)
            btnLoggedAs.text = "Logged as " + auth.currentUser?.email
        }

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )

        AlbumService.refreshUserAlbums()
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }


     fun dispatchTakePictureIntent(v:View) {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    // Error occurred while creating the File
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        this,
                        "com.example.android.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
                }
            }
        }
    }

    fun createAlbum(v:View){
        // get input text
        val albumName= this.findViewById<TextInputEditText>(R.id.createAlbum)
        // save on server
        if (albumName != null) {
            AlbumService.albumCreate(albumName.text.toString())
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
//            val imageBitmap = data?.extras?.get("data") as Bitmap?
//            AlbumService.albumCreate("Vacances au sky");
//            if(imageBitmap !== null)
//                Uploader.upload(imageBitmap, "photo");
//            val imageView: ImageView = findViewById(R.id.imageView5)
//            imageView.setImageBitmap(imageBitmap)

//        }
        val baos = ByteArrayOutputStream()
        val imageBitmap = data?.extras?.get("data") as Bitmap?
        imageBitmap?.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()


        val anotherIntent = Intent(this, SavePhotoActivity::class.java)
        anotherIntent.putExtra("imagePath", currentPhotoPath)
        startActivity(anotherIntent)

    }
    @Throws(IOException::class)
    fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

    fun gallery(v : View){
        val anotherIntent = Intent(this, GalleryActivity::class.java)
        startActivity(anotherIntent)
    }
}