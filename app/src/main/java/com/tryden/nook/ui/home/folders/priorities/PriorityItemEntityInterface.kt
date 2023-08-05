package com.tryden.nook.ui.home.folders.priorities

import com.tryden.nook.database.entity.PriorityItemEntity

interface PriorityItemEntityInterface {

    fun onBumpPriority(priorityItemEntity: PriorityItemEntity)
    fun onItemSelected(priorityItemEntity: PriorityItemEntity)

}