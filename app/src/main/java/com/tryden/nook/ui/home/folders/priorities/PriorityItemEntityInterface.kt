package com.tryden.nook.ui.home.folders.priorities

import com.tryden.nook.data.source.local.entity.PriorityItemEntity

interface PriorityItemEntityInterface {

    fun onBumpPriority(priorityItemEntity: PriorityItemEntity)
    fun onItemSelected(priorityItemEntity: PriorityItemEntity)

}