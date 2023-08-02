package com.tryden.nook.ui

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.tryden.nook.R
import com.tryden.nook.arch.NookViewModel
import com.tryden.nook.database.AppDatabase
import com.tryden.nook.databinding.ActivityMainBinding
import com.tryden.nook.databinding.BottomAppBarBinding
import com.tryden.nook.databinding.ModelBottomAppBarHomeBinding
import com.tryden.nook.databinding.ModelBottomAppBarItemEditBinding
import com.tryden.nook.databinding.ModelBottomAppBarItemsListBinding
import com.tryden.nook.ui.home.priorities.PrioritiesFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    // ViewModel fetches items from Room database
    val viewModel: NookViewModel by viewModels()

    // region Bottom Toolbar Variables
    // parent
    lateinit var bottomToolbar: BottomAppBarBinding
    lateinit var bottomToolbarHome: ConstraintLayout
    lateinit var bottomToolbarItemsList: ConstraintLayout
    lateinit var bottomToolbarEditItem: ConstraintLayout
    // children
    lateinit var addFolderImageView: AppCompatImageView
    lateinit var addItemImageViewHome: AppCompatImageView
    lateinit var itemCountTextView: AppCompatTextView
    lateinit var addItemImageViewItemsList: AppCompatImageView
    lateinit var checkListImageView: AppCompatImageView
    lateinit var addImageImageView: AppCompatImageView
    lateinit var colorizeImageView: AppCompatImageView
    lateinit var addItemImageViewEditItem: AppCompatImageView
    // endregion Bottom Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Collect items
        viewModel.collectAllFolders()
        viewModel.collectAllPriorityEntities()
        viewModel.collectAllChecklistItems()
        viewModel.collectAllNotes()

        // Setup bottom toolbar
        bottomToolbarSetup()

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun hideKeyboard(view: View) {
        val imm: InputMethodManager =
            application.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun showKeyboard() {
        val imm: InputMethodManager =
            application.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

    // region Bottom Toolbar Setup
    fun bottomToolbarSetup() {
        // parent
        bottomToolbar = binding.bottomToolbar
        // children
        bottomToolbarHome = bottomToolbar.homeBottomBar.bottomToolbarLayout
        bottomToolbarItemsList = bottomToolbar.listBottomBar.bottomToolbarLayout
        bottomToolbarEditItem = bottomToolbar.itemEditBottomBar.bottomToolbarLayout

        // Home views
        addFolderImageView = bottomToolbar.homeBottomBar.addFolderImageView
        addItemImageViewHome = bottomToolbar.homeBottomBar.addItemImageView

        // Items List views
        itemCountTextView = bottomToolbar.listBottomBar.itemCountTextView
        addItemImageViewItemsList = bottomToolbar.listBottomBar.addItemImageView

        // Edit Item views
        checkListImageView = bottomToolbar.itemEditBottomBar.checkListImageView
        addImageImageView = bottomToolbar.itemEditBottomBar.addImageImageView
        colorizeImageView = bottomToolbar.itemEditBottomBar.colorizeImageView
        addItemImageViewEditItem = bottomToolbar.itemEditBottomBar.addItemImageView
    }
    // endregion Bottom Toolbar Setup

}