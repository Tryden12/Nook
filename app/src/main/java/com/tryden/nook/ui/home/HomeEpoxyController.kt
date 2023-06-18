package com.tryden.nook.ui.home

import com.airbnb.epoxy.EpoxyController
import com.tryden.nook.ui.epoxy.models.HeadingPageTitleEpoxyModel
import com.tryden.nook.ui.epoxy.models.HeadingSectionTitleEpoxyModel
import com.tryden.nook.ui.epoxy.models.SectionFooterRoundedEpoxyModel
import com.tryden.nook.ui.epoxy.models.SectionHeaderTopRoundEpoxyModel
import com.tryden.nook.ui.epoxy.models.priorities.SectionFolderItemEpoxyModel

class HomeEpoxyController(
    val onFolderItemSelected: () -> Unit
): EpoxyController() {

    var isLoading: Boolean = false
        set(value) {
            field = value
            if (field) {
                requestModelBuild()
            }
        }

    override fun buildModels() {
        if (isLoading) {
            // todo loading state
            return
        }

        // Folders Title
        HeadingPageTitleEpoxyModel("Folders").id("heading-folders").addTo(this)

        // Priorities Section
        HeadingSectionTitleEpoxyModel("Priorities").id("heading-priorities").addTo(this)
        SectionHeaderTopRoundEpoxyModel().id("section-priorities-header-top").addTo(this)
        SectionFolderItemEpoxyModel(
            0, // todo revisit
            "All Priorities",
            0,
            onFolderItemSelected
        ).id("folder-item-priorities").addTo(this)
        SectionFooterRoundedEpoxyModel().id("section-priorities-footer-top").addTo(this)

    }


}