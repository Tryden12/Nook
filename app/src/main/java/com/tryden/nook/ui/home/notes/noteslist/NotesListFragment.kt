package com.tryden.nook.ui.home.notes.noteslist

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.tryden.nook.R
import com.tryden.nook.database.entity.FolderEntity
import com.tryden.nook.database.entity.NoteEntity
import com.tryden.nook.databinding.FragmentNotesListBinding
import com.tryden.nook.ui.BaseFragment
import com.tryden.nook.ui.BottomToolbarSetup
import com.tryden.nook.ui.epoxy.models.BottomSheetViewType
import com.tryden.nook.ui.home.checklists.checklist.ChecklistEpoxyController

class NotesListFragment : BaseFragment() {

    private var _binding: FragmentNotesListBinding? = null
    private val binding get() = _binding!!

    /**
     * The safeArgs here holds the folder title string value needed to filter
     * display only those checklist items.
     *
     * This is passed in from HomeFragment() or AllNotesFoldersFragment() via
     * NavDirections.
     *
     * todo: setup HomeFragment() NavDirections
     */
    private val safeArgs: NotesListFragmentArgs by navArgs()
    private val selectedFolderEntity: FolderEntity? by lazy {
        sharedViewModel.foldersLiveData.value?.find {
            it.title == safeArgs.folderTitle
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentNotesListBinding.inflate(inflater, container,false)
        return  binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tag = resources.getString(R.string.notes_list_fragment_key)
        Log.d(tag, "onViewCreated: $tag")

        // Bottom Sheet Type = Note Item
        sharedViewModel.updateBottomSheetItemType(BottomSheetViewType.Type.NOTE_ITEM)

        // Setup Bottom Toolbar
        BottomToolbarSetup(
            fragmentKey = mainActivity.getString(R.string.notes_list_fragment_key),
            activity = mainActivity
        ).bottomToolbarSetup()

        // Setup Epoxy Controller
        val controller = NotesListEpoxyController()
        binding.epoxyRecyclerView.setController(controller)

        // Set Action Bar Title
        mainActivity.supportActionBar?.title = safeArgs.folderTitle

        sharedViewModel.noteEntitiesLiveData.observe(viewLifecycleOwner) { list ->
            val items = list.filter {
                it.folderName == safeArgs.folderTitle
            }

            when (items.size) {
                1 -> {
                    mainActivity.itemCountTextView.text = "${items.size} note"
                }
                else -> {
                    mainActivity.itemCountTextView.text = "${items.size} notes"
                }
            }

            controller.itemEntityList = items as ArrayList<NoteEntity>
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}