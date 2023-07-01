package com.tryden.nook.ui.home.checklists

import com.airbnb.epoxy.EpoxyController
import com.tryden.nook.application.NookApplication
import com.tryden.nook.ui.epoxy.models.LoadingEpoxyModel

class ChecklistsEpoxyController() : EpoxyController()  {

    val context = NookApplication.context

    var isLoading: Boolean = true
        set(value) {
            field = value
            if (field) {
                requestModelBuild()
            }
        }

    // todo itemEntityList = ArrayList<ChecklistItemEntity>()


    override fun buildModels() {
        if (isLoading) {
            LoadingEpoxyModel().id("loading-state").addTo(this)
            return
        }
    }
}