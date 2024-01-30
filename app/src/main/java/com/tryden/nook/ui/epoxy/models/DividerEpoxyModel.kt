package com.tryden.nook.ui.epoxy.models

import com.tryden.nook.R
import com.tryden.nook.databinding.ModelDividerRecyclerviewBinding
import com.tryden.nook.ui.epoxy.ViewBindingKotlinModel

class DividerEpoxyModel():
    ViewBindingKotlinModel<ModelDividerRecyclerviewBinding>(R.layout.model_divider_recyclerview) {

    override fun ModelDividerRecyclerviewBinding.bind() {
        // nothing to do
    }
}