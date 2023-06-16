package com.tryden.nook.ui.epoxy.models

import com.tryden.nook.R
import com.tryden.nook.databinding.ModelHeadingPageTitleBinding
import com.tryden.nook.databinding.ModelHeadingSectionTitleBinding
import com.tryden.nook.ui.epoxy.ViewBindingKotlinModel

data class HeadingSectionTitleEpoxyModel(
    val title: String
): ViewBindingKotlinModel<ModelHeadingSectionTitleBinding>(R.layout.model_heading_section_title) {

    override fun ModelHeadingSectionTitleBinding.bind() {
        titleTextView.text = title
    }
}