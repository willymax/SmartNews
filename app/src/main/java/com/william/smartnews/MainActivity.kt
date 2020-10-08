package com.william.smartnews

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.google.android.material.appbar.MaterialToolbar

class MainActivity : AppCompatActivity() {
    private lateinit var topAppBar: MaterialToolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        topAppBar = findViewById(R.id.topAppBar)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration.Builder(
            R.id.main_fragment
        )
            .build()

        val navController =
            Navigation.findNavController(this, R.id.nav_host_fragment)
        NavigationUI.setupWithNavController(topAppBar, navController, appBarConfiguration)
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            val destinationId = destination.id

        }
    }
}
