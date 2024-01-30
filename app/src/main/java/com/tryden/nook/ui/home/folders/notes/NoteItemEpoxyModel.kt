package com.tryden.nook.ui.home.folders.notes

import com.tryden.nook.R
import com.tryden.nook.data.source.local.entity.NoteEntity
import com.tryden.nook.databinding.ModelNoteItemEntityBinding
import com.tryden.nook.ui.epoxy.ViewBindingKotlinModel
import com.tryden.nook.ui.epoxy.interfaces.EpoxyItemsInterface
import com.tryden.nook.ui.home.OnItemSelected


/**
 * This epoxy model provides the layout file for checklist item entity in ChecklistFragment().
 */

class NoteItemEpoxyModel(
    val itemEntity: NoteEntity,
    val onItemSelected: OnItemSelected
): ViewBindingKotlinModel<ModelNoteItemEntityBinding>(R.layout.model_note_item_entity) {

    override fun ModelNoteItemEntityBinding.bind() {
        titleTextView.text = itemEntity.title
        descriptionTextView.text = itemEntity.description
        lastModifiedTextView.text = itemEntity.lastModifiedFormatted

        root.setOnClickListener {
            /**
             * Wrap the NoteEntity in an EpoxyItemsInterface type.
             * At Fragment level, onItemSelected() will determine the type of item
             * using a when statement.
             */
            val entity = EpoxyItemsInterface.NoteItem(itemEntity)
            onItemSelected.onItemSelected(entity)
        }
    }


}