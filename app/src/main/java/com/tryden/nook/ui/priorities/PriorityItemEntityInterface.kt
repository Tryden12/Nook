package com.tryden.nook.ui.priorities

import com.tryden.nook.database.entity.PriorityItemEntity

interface PriorityItemEntityInterface {

    fun onBumpPriority(priorityItemEntity: PriorityItemEntity)
}