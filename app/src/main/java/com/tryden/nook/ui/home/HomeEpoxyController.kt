package com.tryden.nook.ui.home

import androidx.core.content.ContextCompat
import com.airbnb.epoxy.EpoxyController
import com.tryden.nook.R
import com.tryden.nook.application.NookApplication
import com.tryden.nook.ui.epoxy.models.*

class HomeEpoxyController(
    val onFolderItemSelected: OnFolderSelectedInterface
): EpoxyController() {

    val context = NookApplication.context

    var isLoading: Boolean = false
        set(value) {
            field = value
            if (field) {
                requestModelBuild()
            }
        }

    var prioritiesItemCount: String = ""
        set(value) {
            field = value
        }

    override fun buildModels() {
        if (isLoading) {
            // todo loading state
            return
        }

        // Folders Title
        HeadingPageTitleEpoxyModel("Folders").id("heading-folders").addTo(this)

        // Priorities Section
        HeadingSectionTitleEpoxyModel("Priorities", showDropDown = true).id("heading-priorities").addTo(this)
        SectionHeaderTopRoundEpoxyModel().id("section-priorities-header-top").addTo(this)
        SectionFolderItemEpoxyModel(
            ContextCompat.getDrawable(NookApplication.context, R.drawable.ic_folder),
            context.getString(R.string.all_priorities),
            prioritiesItemCount,
            onFolderItemSelected
        ).id("folder-item-priorities").addTo(this)
        SectionFooterRoundedEpoxyModel().id("section-priorities-footer-top").addTo(this)

        // Spacer
        SpacerEpoxyModel(spacerHeight = 16).id("spacer-1}").addTo(this)

        // Checklist Section
        HeadingSectionTitleEpoxyModel("Checklists", showDropDown = true).id("heading-checklists").addTo(this)
        SectionHeaderTopRoundEpoxyModel().id("section-checklists-header-top").addTo(this)
        SectionFolderItemEpoxyModel(
            ContextCompat.getDrawable(NookApplication.context, R.drawable.ic_folder),
            context.getString(R.string.all_checklists),
            "0", // todo revisit to update dynamically
            onFolderItemSelected
        ).id("folder-item-checklists").addTo(this)
        SectionFooterRoundedEpoxyModel().id("section-checklists-footer-top").addTo(this)

    }


}