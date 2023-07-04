package com.tryden.nook.ui.epoxy.models

import com.tryden.nook.R
import com.tryden.nook.application.NookApplication

data class BottomSheetViewType(
    val type: Type = Type.FOLDER
) {
    enum class Type(val headerTitle: String) {
        NONE(NookApplication.context.getString(R.string.new_item)),
        FOLDER(NookApplication.context.getString(R.string.new_folder)),
        PRIORITY(NookApplication.context.getString(R.string.new_priority)),
        CHECKLIST_ITEM(NookApplication.context.getString(R.string.new_item)),
    }
}