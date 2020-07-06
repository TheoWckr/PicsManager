package com.example.myapplication.ui.notifications

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.myapplication.LoginActivity
import com.example.myapplication.R
import com.example.myapplication.helpers.auth

class NotificationsFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_notifications, container, false)
        auth.signOut()
        val intent = Intent(activity, LoginActivity::class.java)
        activity?.startActivity(intent)
        Toast.makeText(activity?.baseContext, "You are signed out", Toast.LENGTH_LONG)
        activity?.finish()
        return root
    }
}