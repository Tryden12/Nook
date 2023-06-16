package com.tryden.nook.ui.epoxy.models

import com.tryden.nook.R
import com.tryden.nook.databinding.ModelHeadingPageTitleBinding
import com.tryden.nook.ui.epoxy.ViewBindingKotlinModel

data class HeadingPageTitleEpoxyModel(
    val title: String
): ViewBindingKotlinModel<ModelHeadingPageTitleBinding>(R.layout.model_heading_page_title) {

    override fun ModelHeadingPageTitleBinding.bind() {
        titleTextView.text = title
    }
}