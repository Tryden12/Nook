package com.tryden.nook.ui.priorities

import com.tryden.nook.database.entity.PriorityItemEntity

interface PriorityItemEntityInterface {

    fun onDelete(priorityItemEntity: PriorityItemEntity)
    fun onBumpPriority(priorityItemEntity: PriorityItemEntity)
}