package com.example.myapplication.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.myapplication.GalleryActivity
import com.example.myapplication.R
import com.example.myapplication.service.AlbumService
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.fragment_dashboard.*


class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?

    ): View? {
        // use arrayadapter and define an array
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        // access the listView from xml file

        val button = root.findViewById<Button>(R.id.createAlbum)
        button.setOnClickListener { createAlbum() }

        return root
    }

    fun createAlbum(){
        val albumName= activity?.findViewById<TextInputEditText>(R.id.albumNameInput)?.text.toString()

        if (albumName.isNotEmpty()) {
            AlbumService.albumCreate(albumName)
            Toast.makeText(activity?.applicationContext,"Album created", Toast.LENGTH_SHORT).show()
            activity?.findViewById<TextInputEditText>(R.id.albumNameInput)?.clearFocus()
            activity?.findViewById<TextInputEditText>(R.id.albumNameInput)?.text?.clear()
        }
        else Toast.makeText(activity?.baseContext,"Please enter a name for the album", Toast.LENGTH_SHORT).show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userAlbums = ArrayList<String>()
        for (album in AlbumService.albumList) {
            userAlbums.add(album.name)
        }

        val arrayAdapter: ArrayAdapter<*>
        val arrayAdapterOther: ArrayAdapter<*>

//        val userAlbum = arrayOf("caca", "pipi")
//        val userAlbum = arrayOf(
//            userAlbums
//        )


        //ListView for User Album
        val mListView = view.findViewById<ListView>(R.id.list_album)
        arrayAdapter = this.context?.let {
            ArrayAdapter(
                it,
                android.R.layout.simple_list_item_1, userAlbums
            )
        }!!
        if (mListView != null) {
            mListView.adapter = arrayAdapter
        }


        mListView.setOnItemClickListener { parent, view, position, id ->
            val element =  parent.getItemAtPosition(position) as String // The item that was clicked
            val intent = Intent(activity, GalleryActivity::class.java)
            intent.putExtra("albumName", element)
            startActivity(intent)
        }

        //ListView forOther Albums

        val userAlbumsOther = ArrayList<String>()
        for (album in AlbumService.albumListOtherUsers) {
            userAlbumsOther.add(album.name)
        }

        val mListViewOther = view.findViewById<ListView>(R.id.list_album_other)
        arrayAdapterOther = this.context?.let {
            ArrayAdapter(
                it,
                android.R.layout.simple_list_item_1, userAlbumsOther
            )
        }!!
        if (mListViewOther != null) {
            mListViewOther.adapter = arrayAdapterOther
        }

        mListViewOther.setOnItemClickListener { parent, view, position, id ->
            val element =  parent.getItemAtPosition(position) as String // The item that was clicked
            val intent = Intent(activity, GalleryActivity::class.java)
            intent.putExtra("albumName", element)
            intent.putExtra("isOther", "true")
            startActivity(intent)
        }

    }



    fun gallery(v : View){
        val anotherIntent = Intent(activity, GalleryActivity::class.java)
        anotherIntent.putExtra("albumName", "new album")
        startActivity(anotherIntent)
    }

}