package com.tryden.nook.ui.home.bottomsheet

import android.util.Log
import com.airbnb.epoxy.EpoxyController
import com.tryden.nook.database.entity.FolderEntity
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

    override fun buildModels() {

        when (currentBottomSheetType) {
            FOLDER -> {
                BottomSheetAddFolderEpoxyModel(
                    folderType = FOLDER.key,
                    onAddItemSheetButtonSelected
                ).id("add-folder-epoxy-model").addTo(this)
            }
            FOLDER_PRIORITY -> {
                BottomSheetAddFolderEpoxyModel(
                    folderType = FOLDER_PRIORITY.key,
                    onAddItemSheetButtonSelected
                ).id("add-folder-epoxy-model").addTo(this)
            }
            FOLDER_CHECKLIST -> {
                BottomSheetAddFolderEpoxyModel(
                    folderType = FOLDER_CHECKLIST.key,
                    onAddItemSheetButtonSelected
                ).id("add-folder-epoxy-model").addTo(this)
            }
            FOLDER_NOTE -> {
                // todo revisit once needed
            }
            PRIORITY -> {
                BottomSheetAddPriorityItemEpoxyModel(
                    onAddItemSheetButtonSelected
                ).id("add-priority-item-epoxy-model").addTo(this)
            }
            CHECKLIST_ITEM -> {
                BottomSheetAddChecklistItemEpoxyModel(
                    currentFolderSelected,
                    onAddItemSheetButtonSelected
                ).id("add-checklist-item-epoxy-model").addTo(this)
            }
            else -> {
                // do nothing
                Log.e("AddItemSheetEpoxyController()", "currentBottomSheetType ELSE case" )
            }
        }

    }
}
