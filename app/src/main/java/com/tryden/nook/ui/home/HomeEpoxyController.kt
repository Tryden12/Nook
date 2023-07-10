package com.tryden.nook.ui.home

import android.util.Log
import androidx.core.content.ContextCompat
import com.airbnb.epoxy.EpoxyController
import com.tryden.nook.R
import com.tryden.nook.application.NookApplication
import com.tryden.nook.database.entity.FolderEntity
import com.tryden.nook.ui.epoxy.models.*
import java.util.UUID

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

    var folders = ArrayList<FolderEntity>()
        set(value) {
            field = value
        }

    var prioritiesItemCount: String = ""
        set(value) {
            field = value
        }

    var checklistsCount: String = ""
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
            folderEntity = FolderEntity(
                id = 0,
                title = context.getString(R.string.all_priorities),
                type = "Folder",
                size = prioritiesItemCount
            ),
            ContextCompat.getDrawable(NookApplication.context, R.drawable.ic_folder),
            onFolderItemSelected
        ).id("folder-item-priorities").addTo(this)
        SectionFooterRoundedEpoxyModel().id("section-priorities-footer-top").addTo(this)

        // Spacer
        SpacerEpoxyModel(spacerHeight = 16).id("spacer-1}").addTo(this)

        // Checklist Section
        HeadingSectionTitleEpoxyModel("Checklists", showDropDown = true).id("heading-checklists").addTo(this)
        SectionHeaderTopRoundEpoxyModel().id("section-checklists-header-top").addTo(this)
        SectionFolderItemEpoxyModel(
            folderEntity = FolderEntity(
                id = 0,
                title = context.getString(R.string.all_checklists),
                type = "Folder",
                size = checklistsCount
            ),
            ContextCompat.getDrawable(NookApplication.context, R.drawable.ic_folder),
            onFolderItemSelected
        ).id("folder-all-checklists").addTo(this)
        // List all folders with type "Checklist"
        folders.forEachIndexed { index, folder ->
            if (folder.type == context.getString(R.string.checklist)) {
                Log.d("HomeEpoxyController", folder.title)
                SectionFolderItemEpoxyModel(
                    folderEntity = FolderEntity(
                        id = folder.id,
                        title = folder.title,
                        type = folder.type,
                        size = folder.size
                    ),
                    ContextCompat.getDrawable(NookApplication.context, R.drawable.ic_folder),
                    onFolderItemSelected
                ).id("folder-${folder.title}-checklist").addTo(this)
            }
        }
        SectionFooterRoundedEpoxyModel().id("section-checklists-footer-top").addTo(this)

    }


}