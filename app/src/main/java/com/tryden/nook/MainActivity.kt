package com.tryden.nook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.tryden.nook.arch.NookViewModel
import com.tryden.nook.database.AppDatabase

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val viewModel: NookViewModel by viewModels()
        viewModel.init(AppDatabase.getDatabase(this))
    }
}