package com.tryden.nook.database.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.text.DateFormat

@Entity(tableName = "note_entity")
@Parcelize
class NoteEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val lastModified: Long = System.currentTimeMillis(),
    val categoryId: String = "",
    val folderName: String = ""
): Parcelable {
    val createdAtFormatted: String
        get() = DateFormat.getDateInstance(3).format(createdAt)

    val lastModifiedFormatted: String
        get() = DateFormat.getDateInstance(3).format(lastModified)
}