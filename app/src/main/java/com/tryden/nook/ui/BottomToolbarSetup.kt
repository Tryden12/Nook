package com.tryden.nook.ui

import android.util.Log
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import com.tryden.nook.R

/**
 * This class determines which bottom toolbar to display depending on the Fragment currently shown.
 * Because there are 3 different bottom toolbar combinations and they must stay at Activity level,
 * we pass in the two params to provide context.
 *
 * @param fragmentKey: String resource indicating current fragment
 * @param activity: MainActivity context to access String res and variables/methods
 *
 * The method `bottomToolbarSetup()` runs from each fragment's `onViewCreated()`.
 */
data class BottomToolbarSetup(
    private val fragmentKey: String,
    private val activity: MainActivity,
){
    fun bottomToolbarSetup() {
        Log.e("BottomToolbarSetup()", "bottomToolbarSetup: fragmentKey = $fragmentKey", )

        when (fragmentKey) {
            /** Home Fragment *********************************************************************/
            activity.getString(R.string.home_fragment_key) -> {

                // Show correct toolbar, hide others
                activity.bottomToolbarHome.isVisible = true
                activity.bottomToolbarEditItem.isInvisible = true
                activity.bottomToolbarItemsList.isInvisible = true

                // OnClick listeners
                activity.addFolderImageView.setOnClickListener {
                    Log.e("BottomToolbarSetup()", "addFolderImageView clicked!", )
                }
                activity.addItemImageViewHome.setOnClickListener {
                    Log.e("BottomToolbarSetup()", "addItemImageViewHome clicked!", )
                    activity.navController.navigate(R.id.action_homeFragment_to_addPriorityFragment)
                }

            }

            /** Priorities Fragment **************************************************************/
            activity.getString(R.string.priorities_fragment_key) -> {
                // Show correct toolbar, hide others
                activity.bottomToolbarItemsList.isVisible = true
                activity.bottomToolbarHome.isInvisible = true
                activity.bottomToolbarEditItem.isInvisible = true

                // OnClick listeners
                activity.addItemImageViewItemsList.setOnClickListener {
                    Log.e("BottomToolbarSetup()", "addItemImageViewItemsList clicked!", )
                    activity.navController.navigate(R.id.action_prioritiesFragment_to_addPriorityFragment)
                }
            }

            /** Add Priority Fragment *************************************************************/
            activity.getString(R.string.add_priority_fragment_key) -> {
                // Hide all bottom toolbars
                activity.bottomToolbarItemsList.isInvisible = true
                activity.bottomToolbarHome.isInvisible = true
                activity.bottomToolbarEditItem.isInvisible = true
            }

            /** Checklists Fragment **************************************************************/
            activity.getString(R.string.checklists_fragment_key) -> {
                // Show correct toolbar, hide others
                activity.bottomToolbarItemsList.isVisible = true
                activity.bottomToolbarHome.isInvisible = true
                activity.bottomToolbarEditItem.isInvisible = true

                // OnClick listeners
                activity.addItemImageViewItemsList.setOnClickListener {
                    Log.e("BottomToolbarSetup()", "addItemImageViewItemsList clicked!", )
//                    activity.navController.navigate(R.id.) todo: revisit once AddChecklistFragment created
                }
            }
        }
    }
}