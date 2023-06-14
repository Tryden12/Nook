package com.tryden.nook.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.tryden.nook.MainActivity
import com.tryden.nook.arch.NookViewModel
import com.tryden.nook.database.AppDatabase

abstract class BaseFragment : Fragment() {

    protected val mainActivity: MainActivity
        get() = activity as MainActivity

    protected val appDatabase: AppDatabase
        get() = AppDatabase.getDatabase(requireActivity())

    protected val sharedViewModel: NookViewModel by activityViewModels()
}