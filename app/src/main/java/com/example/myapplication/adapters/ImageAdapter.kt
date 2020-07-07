package com.example.myapplication.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.google.firebase.ktx.Firebase
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


            val storageReference = Firebase.storage.reference
            val pictureRef=  storageReference.child("9iWK6LwOOmOYU3bKC4DQeU8LPCH3 /Johnny")
// ImageView in your Activity

// Download directly from StorageReference using Glide
// (See MyAppGlideModule for Loader registration)
            Glide.with(holder.imageView.context /* context */)
                .load("https://firebasestorage.googleapis.com/v0/b/picts-manager.appspot.com/o/9iWK6LwOOmOYU3bKC4DQeU8LPCH3%20%2FJohnny?alt=media&token=3ea74873-f04e-46fd-8b0e-6e1a8f76384a")
                .into(holder.imageView )
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
        }

        // Return the size of your dataset (invoked by the layout manager)
        override fun getItemCount() = myDataset.size
    }


