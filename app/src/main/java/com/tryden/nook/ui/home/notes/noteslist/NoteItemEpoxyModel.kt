package com.tryden.nook.ui.home.notes.noteslist

import com.tryden.nook.R
import com.tryden.nook.database.entity.NoteEntity
import com.tryden.nook.databinding.ModelChecklistItemEntityBinding
import com.tryden.nook.databinding.ModelNoteItemEntityBinding
import com.tryden.nook.ui.epoxy.ViewBindingKotlinModel


/**
 * This epoxy model provides the layout file for checklist item entity in ChecklistFragment().
 */

class NoteItemEpoxyModel(
    val itemEntity: NoteEntity
): ViewBindingKotlinModel<ModelNoteItemEntityBinding>(R.layout.model_note_item_entity) {

    override fun ModelNoteItemEntityBinding.bind() {
        titleTextView.text = itemEntity.title
        descriptionTextView.text = itemEntity.description
        lastModifiedTextView.text = itemEntity.lastModifiedFormatted
    }


}