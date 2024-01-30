package com.tryden.nook.data.source.local.dao

import androidx.room.*
import com.tryden.nook.data.source.local.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for the Note table.
 */
@Dao
interface NoteEntityDao {

    @Query("SELECT * FROM note_entity")
    fun getAllNoteEntities(): Flow<List<NoteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(noteEntity: NoteEntity)

    @Update
    suspend fun update(noteEntity: NoteEntity)

    @Delete
    suspend fun delete(noteEntity: NoteEntity)
}