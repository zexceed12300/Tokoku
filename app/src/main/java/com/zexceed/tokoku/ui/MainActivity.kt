package com.zexceed.tokoku.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.zexceed.tokoku.R
import com.zexceed.tokoku.databinding.ActivityMainBinding
import com.zexceed.tokoku.ui.home.HomeViewModel
import com.zexceed.tokoku.util.AuthPreferences
import com.zexceed.tokoku.util.ViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var preferences: AuthPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        preferences = AuthPreferences(this@MainActivity)

        if (!preferences.isTokenExist()) {
            intent = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(intent)
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.hide()

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_profile
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    private fun obtainViewModel(mainActivity: MainActivity): HomeViewModel {
        val factory = ViewModelFactory.getInstance(mainActivity.application)
        return ViewModelProvider(mainActivity, factory)[HomeViewModel::class.java]
    }
}