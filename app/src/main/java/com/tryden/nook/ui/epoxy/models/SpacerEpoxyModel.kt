package com.tryden.nook.ui.epoxy.models

import com.tryden.nook.R
import com.tryden.nook.databinding.ModelSpacerBinding
import com.tryden.nook.ui.epoxy.ViewBindingKotlinModel


data class SpacerEpoxyModel(
    val spacerHeight: Int = 10,
): ViewBindingKotlinModel<ModelSpacerBinding>(R.layout.model_spacer) {

    override fun ModelSpacerBinding.bind() {

        // Change layout params height
        val lp = rootConstraintLayout.layoutParams
        lp.height = spacerHeight
        rootConstraintLayout.layoutParams = lp
    }
}