package com.tryden.nook.ui

import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavDirections
import com.tryden.nook.R
import com.tryden.nook.arch.NookViewModel
import com.tryden.nook.database.AppDatabase

abstract class BaseFragment : Fragment() {

    protected val mainActivity: MainActivity
        get() = activity as MainActivity

//    protected val appDatabase: AppDatabase
//        get() = AppDatabase.getDatabase(requireActivity())

    protected val sharedViewModel: NookViewModel by activityViewModels()

    // region Navigation helper methods
    protected fun navigateUp() {
        mainActivity.navController.navigateUp()
    }

    protected fun navigateViewNavGraph(actionId: Int) {
        mainActivity.navController.navigate(actionId)
    }

    protected fun navigateViewNavGraph(directions: NavDirections) {
        mainActivity.navController.navigate(directions)
    }
    // endregion Navigation helper methods
}