package com.tryden.nook.ui.home.folders.checklist

import com.tryden.nook.database.entity.ChecklistItemEntity

interface OnCheckSelected {

    fun onCheckboxChecked(itemEntity: ChecklistItemEntity, isChecked: Boolean)

    fun onItemSelected(itemEntity: ChecklistItemEntity)

}