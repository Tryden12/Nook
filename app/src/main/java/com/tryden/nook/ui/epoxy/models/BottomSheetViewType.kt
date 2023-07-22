package com.tryden.nook.ui.epoxy.models

import com.tryden.nook.R
import com.tryden.nook.application.NookApplication

data class BottomSheetViewType(
    val type: Type = Type.FOLDER
) {
    enum class Type(val key: String) {
        NONE(NookApplication.context.getString(R.string.new_item)),

        // Folder (general) from home screen
        FOLDER(NookApplication.context.getString(R.string.folder_type_general)),

        // Folder (specific) from specific fragment (ex. add folder from AllChecklistsFragment)
        FOLDER_PRIORITY(NookApplication.context.getString(R.string.priority_type_key)),
        FOLDER_CHECKLIST(NookApplication.context.getString(R.string.checklist_type_key)),
        FOLDER_NOTE(NookApplication.context.getString(R.string.note_type_key)),

        // Items
        PRIORITY(NookApplication.context.getString(R.string.new_priority)),
        CHECKLIST_ITEM(NookApplication.context.getString(R.string.new_item)),
        NOTE_ITEM(NookApplication.context.getString(R.string.new_note)),
    }
}