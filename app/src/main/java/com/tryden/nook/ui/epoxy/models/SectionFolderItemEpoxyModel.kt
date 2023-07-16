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
        if (folderEntity.size.isEmpty()) {
            countTextView.text = "0"
        } else {
            countTextView.text = folderEntity.size
        }

        root.setOnClickListener {
//            when (folderEntity.title) {
//                NookApplication.context.getString(R.string.all_priorities) -> {
//                    onFolderSelected.onPriorityFolderSelected()
//                }
//                NookApplication.context.getString(R.string.all_checklists) -> {
//                    onFolderSelected.onChecklistFolderSelected(NookApplication.context.getString(R.string.all_checklists))
//                }
//                NookApplication.context.getString(R.string.all_notes) -> {
//                    onFolderSelected.onNoteFolderSelected()
//                }
//            }

            when (folderEntity.type) {
                NookApplication.context.getString(R.string.priority) -> {
                    onFolderSelected.onPriorityFolderSelected()
                }
                NookApplication.context.getString(R.string.checklist) -> {
                    onFolderSelected.onChecklistFolderSelected(folderEntity)
                }
                NookApplication.context.getString(R.string.note) -> {
                    onFolderSelected.onNoteFolderSelected() // todo revisit
                }
            }
        }
    }
}