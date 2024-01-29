package com.tryden.nook.ui.home.bottomsheet

import android.util.Log
import com.airbnb.epoxy.EpoxyController
import com.tryden.nook.data.database.entity.ChecklistItemEntity
import com.tryden.nook.data.database.entity.FolderEntity
import com.tryden.nook.data.database.entity.NoteEntity
import com.tryden.nook.data.database.entity.PriorityItemEntity
import com.tryden.nook.ui.epoxy.models.BottomSheetViewType
import com.tryden.nook.ui.epoxy.models.BottomSheetViewType.Type.*

/**
 * This controller provides the layout & epoxy model based on the currentBottomSheetType.
 *
 * The following are updated from the Fragment level via a shared view model:
 * 1. currentBottomSheetType
 * 2. currentFolderSelected
 */

class AddItemSheetEpoxyController(
    private val onAddItemSheetButtonSelected: OnAddItemSheetButtonSelected
): EpoxyController() {

    var currentBottomSheetType: BottomSheetViewType.Type = NONE
        set(value) {
            field = value
            requestModelBuild()
        }

    var currentFolderSelected: FolderEntity = FolderEntity()
        set(value) {
            field = value
        }

    var currentPrioritySelected: PriorityItemEntity = PriorityItemEntity()
        set(value) {
            field = value
        }

    var currentChecklistItemSelected: ChecklistItemEntity = ChecklistItemEntity()
        set(value) {
            field = value
        }

    var currentNoteSelected: NoteEntity = NoteEntity()
        set(value) {
            field = value
        }

    var editMode: Boolean = false
        set(value) {
            field = value
        }

    override fun buildModels() {
        Log.d("AddItemSheetEpoxyController", "currentBottomSheetType = ${currentBottomSheetType.name}")

        when (currentBottomSheetType) {

            /** Folders: use currentBottomSheetType **/
            FOLDER -> {
                BottomSheetAddFolderEpoxyModel(
                    folderType = FOLDER.key,
                    onAddItemSheetButtonSelected = onAddItemSheetButtonSelected
                ).id("add-folder-epoxy-model").addTo(this)
            }
            FOLDER_PRIORITY -> {
                BottomSheetAddFolderEpoxyModel(
                    folderType = FOLDER_PRIORITY.key,
                    onAddItemSheetButtonSelected = onAddItemSheetButtonSelected
                ).id("add-folder-epoxy-model").addTo(this)
            }
            FOLDER_CHECKLIST -> {
                BottomSheetAddFolderEpoxyModel(
                    folderType = FOLDER_CHECKLIST.key,
                    onAddItemSheetButtonSelected = onAddItemSheetButtonSelected
                ).id("add-folder-epoxy-model").addTo(this)
            }
            FOLDER_NOTE -> {
                BottomSheetAddFolderEpoxyModel(
                    folderType = FOLDER_NOTE.key,
                    onAddItemSheetButtonSelected = onAddItemSheetButtonSelected
                ).id("add-folder-epoxy-model").addTo(this)
            }

            /** Items: use  currentFolderSelected **/
            PRIORITY -> {
                BottomSheetAddPriorityItemEpoxyModel(
                    editMode = editMode,
                    currentPrioritySelected = currentPrioritySelected,
                    currentFolderEntity = currentFolderSelected,
                    onAddItemSheetButtonSelected = onAddItemSheetButtonSelected
                ).id("add-priority-item-epoxy-model").addTo(this)
            }
            CHECKLIST_ITEM -> {
                BottomSheetAddChecklistItemEpoxyModel(
                    editMode = editMode,
                    currentFolderEntity = currentFolderSelected,
                    currentChecklistItemSelected = currentChecklistItemSelected,
                    onAddItemSheetButtonSelected = onAddItemSheetButtonSelected
                ).id("add-checklist-item-epoxy-model").addTo(this)
            }

            NOTE_ITEM -> {
                BottomSheetAddNoteItemEpoxyModel(
                    editMode = editMode,
                    currentFolderEntity = currentFolderSelected,
                    currentNoteEntity = currentNoteSelected,
                    onAddItemSheetButtonSelected = onAddItemSheetButtonSelected
                ).id("add-checklist-item-epoxy-model").addTo(this)
            }

            else -> {
                // do nothing
                Log.e("AddItemSheetEpoxyController()", "currentBottomSheetType ELSE case" )
            }
        }

    }
}
