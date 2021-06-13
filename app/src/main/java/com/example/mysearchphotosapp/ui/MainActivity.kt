 package com.example.mysearchphotosapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mysearchphotosapp.R
import com.example.mysearchphotosapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

 @AndroidEntryPoint
 class MainActivity : AppCompatActivity(R.layout.activity_main) {

     private val binding: ActivityMainBinding by viewBinding()
     private lateinit var navContoller: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupNavControllerWithAppBar()

    }

    private fun setupNavControllerWithAppBar(){
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        navContoller = navHostFragment.findNavController()
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.galleryFragment)
        )
        setupActionBarWithNavController(navContoller, appBarConfiguration)
    }

     override fun onSupportNavigateUp(): Boolean {
         return navContoller.navigateUp() || super.onSupportNavigateUp()
     }
 }

