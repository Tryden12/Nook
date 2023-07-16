package com.tryden.nook.ui.home.bottomsheet

import android.util.Log
import com.airbnb.epoxy.EpoxyController
import com.tryden.nook.ui.epoxy.models.BottomSheetViewType
import com.tryden.nook.ui.epoxy.models.BottomSheetViewType.Type.*

class AddItemSheetEpoxyController(
    private val onAddItemSheetButtonSelected: OnAddItemSheetButtonSelected
): EpoxyController() {

    var currentBottomSheetType: BottomSheetViewType.Type = NONE
        set(value) {
            field = value
            requestModelBuild()
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
                    folderType = FOLDER_CHECKLIST.key,
                    onAddItemSheetButtonSelected
                ).id("add-folder-epoxy-model").addTo(this)
            }
            FOLDER_CHECKLIST -> {
                BottomSheetAddFolderEpoxyModel(
                    folderType = FOLDER_PRIORITY.key,
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
