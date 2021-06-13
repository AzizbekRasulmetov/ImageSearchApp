package com.example.mysearchphotosapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mysearchphotosapp.R
import com.example.mysearchphotosapp.databinding.FragmentPhotoBinding

class PhotoFragment : Fragment(R.layout.fragment_photo) {

    private val binding: FragmentPhotoBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}