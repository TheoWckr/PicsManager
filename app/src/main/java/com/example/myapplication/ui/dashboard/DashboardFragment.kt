package com.example.myapplication.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
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
        val userAlbums = ArrayList<String>()
        for (album in AlbumService.albumList) {
            userAlbums.add(album.name )}

        val arrayAdapter: ArrayAdapter<*>
        val userAlbum = arrayOf(
            userAlbums
        )

        var mListView = view?.findViewById<ListView>(R.id.list_album)
        arrayAdapter = this.context?.let {
            ArrayAdapter(
                it,
                android.R.layout.simple_list_item_1, userAlbum)
        }!!
        if (mListView != null) {
            mListView.adapter = arrayAdapter
        }

        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
//        val textView: TextView = root.findViewById(R.id.text_dashboard)
//        dashboardViewModel.text.observe(viewLifecycleOwner, Observer {
////            textView.text = it
////        })



        // access the listView from xml file

        val button = root.findViewById<Button>(R.id.createAlbum)
        button.setOnClickListener { createAlbum }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




    fun createAlbum(){
        // get input text
        val albumName= activity?.findViewById<TextInputEditText>(R.id.albumNameInput)
        // save on server
        if (albumName != null) {
            AlbumService.albumCreate(albumName.text.toString())
//            println("on est passé par là")
        }
    }

}}