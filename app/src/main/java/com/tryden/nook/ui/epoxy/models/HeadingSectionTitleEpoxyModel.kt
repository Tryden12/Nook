package com.tryden.nook.ui.epoxy.models

import androidx.core.view.isVisible
import com.tryden.nook.R
import com.tryden.nook.databinding.ModelHeadingPageTitleBinding
import com.tryden.nook.databinding.ModelHeadingSectionTitleBinding
import com.tryden.nook.ui.epoxy.ViewBindingKotlinModel

data class HeadingSectionTitleEpoxyModel(
    val title: String,
    val showDropDown: Boolean
): ViewBindingKotlinModel<ModelHeadingSectionTitleBinding>(R.layout.model_heading_section_title) {

    override fun ModelHeadingSectionTitleBinding.bind() {
        titleTextView.text = title

        dropDownArrowImageView.isVisible = showDropDown
    }
}