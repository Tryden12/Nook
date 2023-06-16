package com.tryden.nook.ui.epoxy.models.priorities

import com.tryden.nook.R
import com.tryden.nook.databinding.ModelSectionFolderItemBinding
import com.tryden.nook.ui.epoxy.ViewBindingKotlinModel

data class SectionFolderItemEpoxyModel(
    val icon: Int,
    val title: String,
    val count: Int
): ViewBindingKotlinModel<ModelSectionFolderItemBinding>(R.layout.model_section_folder_item) {

    override fun ModelSectionFolderItemBinding.bind() {
//        iconImageView.setImageResource(icon)  // todo revisit
        titleTextView.text = title
        countTextView.text = count.toString()
    }
}