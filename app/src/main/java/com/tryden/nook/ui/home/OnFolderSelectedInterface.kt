package com.tryden.nook.ui.home

import com.tryden.nook.database.entity.FolderEntity

interface OnFolderSelectedInterface {

    fun onPriorityFolderSelected()
    fun onChecklistFolderSelected(selectedFolder: FolderEntity)
    fun onNoteFolderSelected(selectedFolder: FolderEntity)

}