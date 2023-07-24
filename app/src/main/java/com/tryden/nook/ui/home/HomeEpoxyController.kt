package com.tryden.nook.ui.home

import android.util.Log
import androidx.core.content.ContextCompat
import com.airbnb.epoxy.EpoxyController
import com.tryden.nook.R
import com.tryden.nook.application.NookApplication
import com.tryden.nook.database.entity.FolderEntity
import com.tryden.nook.database.entity.NoteEntity
import com.tryden.nook.ui.epoxy.interfaces.EpoxyItemsInterface
import com.tryden.nook.ui.epoxy.models.*
import java.util.UUID

/**
 * This is the HomeFragment EpoxyController.
 *
 * @param [onFolderItemSelected] is an interface containing callback methods for
 * when a folder is selected. Its purpose is to help decide whether to navigate to
 * the folder (general) or folder (specific).
 * Example:
 * "All Checklists" folder is folder general -> Navigate to the AllChecklistsFragment.
 * If a user created a checklist folder named "Grocery"...
 * "Grocery" folder is folder specific -> Navigate to the ChecklistFragment.
 */

class HomeEpoxyController(
    val onFolderItemSelected: OnFolderSelectedInterface
): EpoxyController() {

    val context = NookApplication.context

    var isLoading: Boolean = true
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

    var prioritiesItemCount: Int = 0
        set(value) {
            field = value
            isLoading = false
            requestModelBuild()
        }

    var checklistsCount: Int = 0
        set(value) {
            field = value
        }

    var noteFoldersCount: Int = 0
        set(value) {
            field = value
        }

    override fun buildModels() {
        if (isLoading) {
            LoadingEpoxyModel().id("loading_state").addTo(this)
            return
        }

        // Folders Title
        HeadingPageTitleEpoxyModel("Folders").id("heading-folders").addTo(this)

        // region Priorities Section
        HeadingSectionTitleEpoxyModel("Priorities", showDropDown = true).id("heading-priorities").addTo(this)
        SectionHeaderTopRoundEpoxyModel().id("section-priorities-header-top").addTo(this)
        SectionFolderItemEpoxyModel(
            folderEntity = FolderEntity(
                id = 0,
                title = context.getString(R.string.all_priorities),
                type = context.getString(R.string.priority),
                size = prioritiesItemCount
            ),
            ContextCompat.getDrawable(NookApplication.context, R.drawable.ic_folder),
            onFolderItemSelected
        ).id("folder-item-priorities").addTo(this)
        SectionFooterRoundedEpoxyModel().id("section-priorities-footer-top").addTo(this)
        // endregion Priorities Section

        // Spacer
        SpacerEpoxyModel(spacerHeight = 16).id("spacer-1}").addTo(this)

        // region Checklist Section
        HeadingSectionTitleEpoxyModel("Checklists", showDropDown = true).id("heading-checklists").addTo(this)
        SectionHeaderTopRoundEpoxyModel().id("section-checklists-header-top").addTo(this)
        SectionFolderItemEpoxyModel(
            folderEntity = FolderEntity(
                id = 0,
                title = context.getString(R.string.all_checklists),
                type = context.getString(R.string.checklist),
                size = checklistsCount
            ),
            ContextCompat.getDrawable(NookApplication.context, R.drawable.ic_folder),
            onFolderItemSelected
        ).id("folder-all-checklists").addTo(this)
        // List all folders with type "Checklist"
        folders.filter {
            it.type == context.getString(R.string.checklist)
        }.sortedBy {
            it.title
        }.forEachIndexed { index, folder ->
            if (folder.type == context.getString(R.string.checklist)) {
                Log.d("HomeEpoxyController", folder.title)
                // Divider
                DividerFolderEpoxyModel()
                    .id("divider-checklist-folder-$index")
                    .addTo(this)
                // Folder
                SectionFolderItemEpoxyModel(
                    folderEntity = FolderEntity(
                        id = folder.id,
                        title = folder.title,
                        type = folder.type,
                        size = folder.size
                    ),
                    ContextCompat.getDrawable(NookApplication.context, R.drawable.ic_folder),
                    onFolderItemSelected
                ).id("folder-${folder.id}-checklist").addTo(this)
            }
        }
        SectionFooterRoundedEpoxyModel().id("section-checklists-footer-top").addTo(this)
        // endregion Checklist Section

        // Spacer
        SpacerEpoxyModel(spacerHeight = 16).id("spacer-2").addTo(this)


        // region Notes Section
        HeadingSectionTitleEpoxyModel(context.getString(R.string.notes), showDropDown = true)
            .id("heading-${context.getString(R.string.notes)}").addTo(this)
        SectionHeaderTopRoundEpoxyModel().id("section-notes-header-top").addTo(this)
        SectionFolderItemEpoxyModel(
            folderEntity = FolderEntity(
                id = 0,
                title = context.getString(R.string.all_notes),
                type = context.getString(R.string.note),
                size = noteFoldersCount
            ),
            ContextCompat.getDrawable(NookApplication.context, R.drawable.ic_folder),
            onFolderItemSelected
        ).id("folder-all-notes").addTo(this)
        // List all folders with type "Note"
        folders.filter {
            it.type == context.getString(R.string.note)
        }.sortedBy {
            it.title
        }.forEachIndexed { index, folder ->
            Log.d("HomeEpoxyController", folder.title)
            // Divider
            DividerFolderEpoxyModel()
                .id("divider-note-folder-$index")
                .addTo(this)
            // Folder
            SectionFolderItemEpoxyModel(
                folderEntity = FolderEntity(
                    id = folder.id,
                    title = folder.title,
                    type = folder.type,
                    size = folder.size
                ),
                ContextCompat.getDrawable(NookApplication.context, R.drawable.ic_folder),
                onFolderItemSelected
            ).id("folder-${folder.id}-note").addTo(this)
        }
        SectionFooterRoundedEpoxyModel().id("section-note-footer-top").addTo(this)


        // endregion Checklist Section

    }

}