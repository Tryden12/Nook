package com.tryden.nook.ui.home

import android.util.Log
import androidx.core.content.ContextCompat
import com.airbnb.epoxy.EpoxyController
import com.tryden.nook.R
import com.tryden.nook.application.NookApplication
import com.tryden.nook.data.source.local.entity.FolderEntity
import com.tryden.nook.data.source.local.entity.NoteEntity
import com.tryden.nook.ui.epoxy.interfaces.EpoxyItemsInterface
import com.tryden.nook.ui.epoxy.models.*
import java.util.UUID

/**
 * This is the HomeFragment EpoxyController.
 *
 * @param [onFolderItemSelected] is an interface containing callback methods for
 * when a folder is selected. Its purpose is to help decipher which folder type is slected:
 * Priority, Checklist, Note
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
            isLoading = false
            requestModelBuild()
        }

    var prioritiesItemCount: Int = 0
        set(value) {
            field = value
        }

    override fun buildModels() {
        val prioritiesCount = folders.filter { it.type == context.getString(R.string.priority) }.size
        val checklistsCount = folders.filter { it.type == context.getString(R.string.checklist) }.size
        val noteFoldersCount = folders.filter { it.type == context.getString(R.string.note) }.size

        Log.d("HomeEpoxyController()", "buildModels(): \n" +
                "folders size -> ${folders.size} \n" +
                "checklist folders size -> $checklistsCount \n" +
                "note folders size -> $noteFoldersCount\n" +
                "priorities size -> $prioritiesCount \n"
        )

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
                size = prioritiesCount
            ),
            ContextCompat.getDrawable(NookApplication.context, R.drawable.ic_folder),
            onFolderItemSelected
        ).id("folder-item-priorities").addTo(this)
        // List all folders with type "Checklist"
        folders.filter {
            it.type == context.getString(R.string.priority)
        }.sortedBy {
            it.title
        }.forEachIndexed { index, folder ->
            if (folder.type == context.getString(R.string.priority)) {
                Log.d("HomeEpoxyController", folder.title)
                // Divider
                DividerFolderEpoxyModel()
                    .id("divider-priority-folder-$index")
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
                ).id("folder-${folder.id}-priority").addTo(this)
            }
        }
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


        // endregion Notes Section

    }
//
//
//    private fun buildEpoxyList(folders: ArrayList<FolderEntity>): List<EpoxyItemsInterface> {
//        return buildList {
//            // Page title
//            add(HeaderPageTitle(context.getString(R.string.folders_title)))
//            HomeSections.values().forEach {
//                add(HeadingSectionTitleEpoxyModel(title = it.name, showDropDown = true))
//                add(SectionHeaderRounded)
//                add(FolderItem(
//                    item = FolderEntity(
//                        id = 0,
//                        title = "All" + it.name,
//                        type = context.getString(R.string.priority),
//                        size = prioritiesItemCount
//                    )
//                ))
//            }
//
//        }
//    }

    enum class HomeSections() {
        // Items
        Priorities,
        Checklists,
        Notes
    }

}