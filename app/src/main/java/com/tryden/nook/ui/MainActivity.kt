package com.tryden.nook.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import com.tryden.nook.R
import com.tryden.nook.arch.NookViewModel
import com.tryden.nook.database.AppDatabase
import com.tryden.nook.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        val bottomNavigationView: BottomNavigationView = binding.bottomToolbar



        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        //Add custom tab menu
//        val bottomMenuView = bottomNavigationView.getChildAt(0) as BottomNavigationMenuView
//        val view = bottomMenuView.getChildAt(1)
//        val itemView = view as BottomNavigationItemView
//
//        val viewCustom = LayoutInflater.from(this).inflate(R.layout.custom_menu_item_add_folder, bottomMenuView, false)
//        itemView.addView(viewCustom)


//        NavigationUI.setupWithNavController(bottomNavigationView, navController)


        val viewModel: NookViewModel by viewModels()
        viewModel.init(AppDatabase.getDatabase(this))

    }

}