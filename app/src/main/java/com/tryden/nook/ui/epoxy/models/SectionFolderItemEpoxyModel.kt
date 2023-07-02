package com.tryden.nook.ui.epoxy.models

import android.graphics.drawable.Drawable
import androidx.core.view.isVisible
import com.tryden.nook.R
import com.tryden.nook.application.NookApplication
import com.tryden.nook.databinding.ModelSectionFolderItemBinding
import com.tryden.nook.ui.epoxy.ViewBindingKotlinModel
import com.tryden.nook.ui.home.OnFolderSelectedInterface

data class SectionFolderItemEpoxyModel(
    val icon: Drawable?,
    val title: String,
    val count: String,
    val onFolderSelected: OnFolderSelectedInterface
): ViewBindingKotlinModel<ModelSectionFolderItemBinding>(R.layout.model_section_folder_item) {

    override fun ModelSectionFolderItemBinding.bind() {
        iconImageView.setImageDrawable(icon)
        titleTextView.text = title
        if (count.isEmpty()) {
            countTextView.text = "0"
        } else {
            countTextView.text = count
        }

        root.setOnClickListener {
            when (title) {
                NookApplication.context.getString(R.string.all_priorities) -> {
                    onFolderSelected.onPriorityFolderSelected()
                }
                NookApplication.context.getString(R.string.all_checklists) -> {
                    onFolderSelected.onChecklistFolderSelected()
                }
                NookApplication.context.getString(R.string.all_notes) -> {
                    onFolderSelected.onNoteFolderSelected()
                }

            }
        }
    }
}