package com.tryden.nook.ui.epoxy.models

import com.tryden.nook.R
import com.tryden.nook.databinding.ModelEmptyStateBinding
import com.tryden.nook.ui.epoxy.ViewBindingKotlinModel

data class EmptyStateEpoxyModel(
    val title: String,
    val subtitle: String
): ViewBindingKotlinModel<ModelEmptyStateBinding>(R.layout.model_empty_state) {

    override fun ModelEmptyStateBinding.bind() {
        titleTextView.text = title
        subtitleTextview.text = subtitle
    }
}