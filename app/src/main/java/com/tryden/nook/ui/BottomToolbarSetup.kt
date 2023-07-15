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
import com.tryden.nook.ui.home.bottomsheet.AddItemSheet

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
        Log.d("BottomToolbarSetup()", "bottomToolbarSetup: fragmentKey = $fragmentKey", )

        when (fragmentKey) {
            /** HomeFragment *********************************************************************/
            activity.getString(R.string.home_fragment_key) -> {

                // Show correct toolbar, hide others
                activity.bottomToolbarHome.isVisible = true
                activity.bottomToolbarEditItem.isInvisible = true
                activity.bottomToolbarItemsList.isInvisible = true

                // OnClick listeners
                activity.addFolderImageView.setOnClickListener {
                    Log.d("BottomToolbarSetup()", "addFolderImageView clicked!", )
                    AddItemSheet().show(activity.supportFragmentManager, null)

                }
                activity.addItemImageViewHome.setOnClickListener {
                    Log.d("BottomToolbarSetup()", "addItemImageViewHome clicked from HomeFragment!", )
//                    activity.navController.navigate(R.id.action_homeFragment_to_addPriorityFragment)
                }

            }

            /** AddFolder Fragment *************************************************************/
            activity.getString(R.string.add_folder_fragment_key) -> {
                // Hide all bottom toolbars
                activity.bottomToolbarItemsList.isInvisible = true
                activity.bottomToolbarHome.isInvisible = true
                activity.bottomToolbarEditItem.isInvisible = true
            }


            /** Priorities Fragment **************************************************************/
            activity.getString(R.string.priorities_fragment_key) -> {
                // Show correct toolbar, hide others
                activity.bottomToolbarItemsList.isVisible = true
                activity.bottomToolbarHome.isInvisible = true
                activity.bottomToolbarEditItem.isInvisible = true

                // OnClick listeners
                activity.addItemImageViewItemsList.setOnClickListener {
                    Log.d("BottomToolbarSetup()", "addItemImageViewItemsList clicked from PrioritiesFragment!", )
                    activity.navController.navigate(R.id.action_prioritiesFragment_to_addPriorityFragment)
                }
            }

            /** AddPriority Fragment *************************************************************/
            activity.getString(R.string.add_priority_fragment_key) -> {
                // Hide all bottom toolbars
                activity.bottomToolbarItemsList.isInvisible = true
                activity.bottomToolbarHome.isInvisible = true
                activity.bottomToolbarEditItem.isInvisible = true
            }

            /** Checklists Fragment **************************************************************/
            activity.getString(R.string.checklists_fragment_key) -> {
                // Show correct toolbar, hide others
                activity.bottomToolbarHome.isVisible = true
                activity.bottomToolbarItemsList.isInvisible = true
                activity.bottomToolbarEditItem.isInvisible = true

                activity.addFolderImageView.setOnClickListener {
                    AddItemSheet().show(activity.supportFragmentManager, null)

                }
                // OnClick listeners
                activity.addItemImageViewHome.isGone = true // todo: revisit
//                activity.addItemImageViewHome.setOnClickListener {
//                    Log.d("BottomToolbarSetup()", "addItemImageViewHome clicked from ChecklistsFragment!", )
//                    AddItemSheet().show(activity.supportFragmentManager, null)
//                }
            }

            /** Checklist Fragment **************************************************************/
            activity.getString(R.string.checklist_fragment_key) -> {
                // Show correct toolbar, hide others
                activity.bottomToolbarItemsList.isVisible = true
                activity.bottomToolbarHome.isInvisible = true
                activity.bottomToolbarEditItem.isInvisible = true

                activity.addItemImageViewItemsList.setOnClickListener {
                    Log.d("BottomToolbarSetup()",
                        "addItemImageViewItemsList clicked from ChecklistFragment!", )
                    AddItemSheet().show(activity.supportFragmentManager, null)
                }
            }

            /** AddItemSheet Fragment *************************************************************/
            activity.getString(R.string.add_item_bottom_sheet_fragment_key) -> {
                // Hide all bottom toolbars
                activity.bottomToolbarItemsList.isInvisible = true
                activity.bottomToolbarHome.isInvisible = true
                activity.bottomToolbarEditItem.isInvisible = true

                // todo revisit
            }

            /** ELSE *************************************************************/
            else -> {
                // Hide all bottom toolbars
                activity.bottomToolbarItemsList.isInvisible = true
                activity.bottomToolbarHome.isInvisible = true
                activity.bottomToolbarEditItem.isInvisible = true

                Log.d("BottomToolbarSetup()", "ELSE thrown. All bottom toolbars made invisible." )
            }
        }
    }
}