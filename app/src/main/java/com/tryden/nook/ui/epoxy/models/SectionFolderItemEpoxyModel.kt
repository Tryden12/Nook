package com.tryden.nook.ui.epoxy.models

import android.graphics.drawable.Drawable
import androidx.core.view.isVisible
import com.tryden.nook.R
import com.tryden.nook.application.NookApplication
import com.tryden.nook.database.entity.FolderEntity
import com.tryden.nook.databinding.ModelSectionFolderItemBinding
import com.tryden.nook.ui.epoxy.ViewBindingKotlinModel
import com.tryden.nook.ui.home.OnFolderSelectedInterface

data class SectionFolderItemEpoxyModel(
    val folderEntity: FolderEntity,
    val icon: Drawable?,
    val onFolderSelected: OnFolderSelectedInterface
): ViewBindingKotlinModel<ModelSectionFolderItemBinding>(R.layout.model_section_folder_item) {

    override fun ModelSectionFolderItemBinding.bind() {
        iconImageView.setImageDrawable(icon)
        titleTextView.text = folderEntity.title
        if (folderEntity.size == 0) {
            countTextView.text = "0"
        } else {
            countTextView.text = folderEntity.size.toString()
        }

        root.setOnClickListener {
            onFolderSelected.onFolderSelected(folderEntity)
        }
    }
}