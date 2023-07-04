package com.tryden.nook.ui.home.bottomsheet

import com.airbnb.epoxy.EpoxyController
import com.tryden.nook.R
import com.tryden.nook.application.NookApplication
import com.tryden.nook.ui.epoxy.models.BottomSheetAddItem
import com.tryden.nook.ui.epoxy.models.BottomSheetViewType

class AddItemSheetEpoxyController(
    private val onAddItemSheetButtonSelected: OnAddItemSheetButtonSelected
): EpoxyController() {

    var currentBottomSheetType: BottomSheetViewType.Type = BottomSheetViewType.Type.NONE
        set(value) {
            field = value
            requestModelBuild()
        }

    override fun buildModels() {
        BottomSheetAddItem(
            currentBottomSheetType,
            onAddItemSheetButtonSelected
        ).id("header").addTo(this)

    }
}
