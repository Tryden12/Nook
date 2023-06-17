package com.tryden.nook.ui.home

import com.airbnb.epoxy.EpoxyController
import com.tryden.nook.R
import com.tryden.nook.database.entity.PriorityItemEntity
import com.tryden.nook.databinding.ModelItemEntityBinding
import com.tryden.nook.ui.epoxy.ViewBindingKotlinModel
import com.tryden.nook.ui.epoxy.models.HeadingPageTitleEpoxyModel
import com.tryden.nook.ui.epoxy.models.HeadingSectionTitleEpoxyModel
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
        HeadingSectionTitleEpoxyModel("All Priorities").id("heading-priorities").addTo(this)
        SectionFolderItemEpoxyModel(
            0, // todo revisit
            "Priorities",
            0,
            onFolderItemSelected
        ).id("folder-item-priorities").addTo(this)
    }


}