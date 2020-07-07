package com.example.myapplication.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.EditPhotoActivity
import com.example.myapplication.GalleryActivity
import com.example.myapplication.R
import com.example.myapplication.SavePhotoActivity
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage

class ImageAdapter(private val myDataset: Array<String>) :
        RecyclerView.Adapter<ImageAdapter.MyViewHolder>() {

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder.
        // Each data item is just a string in this case that is shown in a TextView.
        class MyViewHolder(val imageView: ImageView) : RecyclerView.ViewHolder(imageView)


        // Create new views (invoked by the layout manager)
        override fun onCreateViewHolder(parent: ViewGroup,
                                        viewType: Int): ImageAdapter.MyViewHolder {
            // create a new view
            val imageView = LayoutInflater.from(parent.context)
                .inflate(R.layout.adapter_image_view, parent, false) as ImageView
            // set the view's size, margins, paddings and layout parameters

            return MyViewHolder(imageView)
        }

        // Replace the contents of a view (invoked by the layout manager)
        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            val photoId = myDataset[position]

            val gsReference: StorageReference =
                com.example.myapplication.helpers.storage.getReferenceFromUrl("gs://picts-manager.appspot.com"+ photoId)

            val storageReference = Firebase.storage.reference
            val pictureRef=  storageReference.child(photoId.removePrefix("/"))
// ImageView in your Activity
            holder.imageView.rotation = 90F


            holder.imageView.setOnClickListener(View.OnClickListener {
                val intent = Intent(it.context, EditPhotoActivity::class.java)
                intent.putExtra("photoId", photoId)
                it.context.startActivity(intent)
            })


// Download directly from StorageReference using Glide
// (See MyAppGlideModule for Loader registration)
            Glide.with(holder.imageView.context /* context */)
                .load(gsReference)
                .into(holder.imageView )
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
        }

        // Return the size of your dataset (invoked by the layout manager)
        override fun getItemCount() = myDataset.size
    }


