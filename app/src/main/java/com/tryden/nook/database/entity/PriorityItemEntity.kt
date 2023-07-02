package com.tryden.nook.database.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = "priority_item_entity")
@Parcelize
data class PriorityItemEntity(
    @PrimaryKey val id: String = "",
    val title: String = "",
    val description: String? = null,
    val lastModified: Long = System.currentTimeMillis(),
    val categoryId: String = "",
    val folderName: String = "",
    val priority: Int = 0
) : Parcelable {
    val lastModifiedFormatted: String
        get() = DateFormat.getDateInstance(3).format(lastModified)
}