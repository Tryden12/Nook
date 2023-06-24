package com.tryden.nook.ui

import android.util.Log
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import com.tryden.nook.R

data class BottomToolbarSetup(
    private val fragmentKey: String,
    private val activity: FragmentActivity,
    private var addItemImageView: AppCompatImageView,
    private var notesCountTextView: AppCompatTextView,
    private var addFolderImageView: AppCompatImageView
){
    fun bottomToolbarSetup() {
        notesCountTextView = activity.findViewById(R.id.countTextView)
        addFolderImageView = activity.findViewById(R.id.addFolderImageView)
        addItemImageView = activity.findViewById(R.id.addItemImageView)

        Log.e("TAG", "bottomToolbar: fragmentKey = $fragmentKey", )

        when (fragmentKey) {
            activity.getString(R.string.home_fragment_key) -> {
                notesCountTextView.isInvisible = true
                addFolderImageView.isVisible = true
                addItemImageView.isVisible = true

            }
            activity.getString(R.string.priorities_fragment_key) -> {
                notesCountTextView.isVisible = true
                addFolderImageView.isGone = true
                addItemImageView.isVisible = true
            }
        }


    }
}