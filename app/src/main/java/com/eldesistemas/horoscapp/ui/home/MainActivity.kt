package com.eldesistemas.horoscapp.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.eldesistemas.horoscapp.R
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.eldesistemas.horoscapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
    }

    private fun initUI() {
        initNavigation()
    }

    private fun initNavigation() {

        val navHost: NavHostFragment = supportFragmentManager
                            .findFragmentById(R.id.fragmentContainerView) as NavHostFragment

        navController = navHost.navController
        binding.bottomNavView.setupWithNavController(navController)
    }
}