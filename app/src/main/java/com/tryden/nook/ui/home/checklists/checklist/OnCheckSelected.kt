package com.tryden.nook.ui.home.checklists.checklist

import com.tryden.nook.database.entity.ChecklistItemEntity

interface OnCheckSelected {

    fun onCheckboxChecked(itemEntity: ChecklistItemEntity, isChecked: Boolean)

}