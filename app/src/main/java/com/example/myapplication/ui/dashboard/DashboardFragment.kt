package com.example.myapplication.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
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
//        val textView: TextView = root.findViewById(R.id.text_dashboard)
//        dashboardViewModel.text.observe(viewLifecycleOwner, Observer {
////            textView.text = it
////        })



        // access the listView from xml file

        val button = root.findViewById<Button>(R.id.createAlbum)
        button.setOnClickListener { createAlbum() }

        return root
    }

    fun createAlbum(){
        // get input text
        val albumName= activity?.findViewById<TextInputEditText>(R.id.albumNameInput)
        // save on server
        if (albumName != null) {
            AlbumService.albumCreate(albumName.text.toString())
//            println("on est passé par là")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userAlbums = ArrayList<String>()
        for (album in AlbumService.albumList) {
            userAlbums.add(album.name)
        }

        val arrayAdapter: ArrayAdapter<*>
//        val userAlbum = arrayOf("caca", "pipi")
//        val userAlbum = arrayOf(
//            userAlbums
//        )

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

//        fun createAlbum(){
//            // get input text
//            val albumName= activity?.findViewById<TextInputEditText>(R.id.albumNameInput)
//            // save on server
//            if (albumName != null) {
//                AlbumService.albumCreate(albumName.text.toString())
////            println("on est passé par là")
//            }
//        }
    }



    fun gallery(v : View){
        val anotherIntent = Intent(activity, GalleryActivity::class.java)
        anotherIntent.putExtra("albumName", "new album")
        startActivity(anotherIntent)
    }

}