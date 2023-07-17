package com.tryden.nook.ui.home.notes

import com.airbnb.epoxy.EpoxyController
import com.tryden.nook.application.NookApplication
import com.tryden.nook.ui.epoxy.models.LoadingEpoxyModel

class AllNotesEpoxyController : EpoxyController() {

    val context = NookApplication.context

    var isLoading: Boolean = true
     set(value) {
         field = value
         if (field) {
             requestModelBuild()
         }
     }


    override fun buildModels() {

        if (isLoading) {
            LoadingEpoxyModel().id("loading-state").addTo(this)
        }

    }
}