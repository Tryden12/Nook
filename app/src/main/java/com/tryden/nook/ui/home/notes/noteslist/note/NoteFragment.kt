package com.tryden.nook.ui.home.notes.noteslist.note

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tryden.nook.R
import com.tryden.nook.databinding.FragmentNoteBinding
import com.tryden.nook.ui.BaseFragment
import com.tryden.nook.ui.BottomToolbarSetup

class NoteFragment : BaseFragment() {

    private var _binding: FragmentNoteBinding? = null
    private val binding get() = _binding!!

//    private val safeArgs: NoteFragmentArgs by navArgs()
//    private val selectedFolderEntity: FolderEntity? by lazy {
//        sharedViewModel.foldersLiveData.value?.find {
//            it.title == safeArgs.folderTitle
//        }
//    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentNoteBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tag = resources.getString(R.string.note_fragment_key)
        Log.d("NoteFragment()", "onViewCreated: $tag")

        // Setup Bottom Toolbar
        BottomToolbarSetup(
            fragmentKey = mainActivity.getString(R.string.note_fragment_key),
            activity = mainActivity
        ).bottomToolbarSetup()

        // Setup Epoxy Controller
        val controller = NoteEpoxyController()
        binding.epoxyRecyclerView.setController(controller)

        // Set Action Bar Title
//        mainActivity.supportActionBar?.title = safeArgs.folderTitle

        sharedViewModel.noteEntitiesLiveData.observe(viewLifecycleOwner) { list ->
//            val items = list.filter {
//                it.folderName == safeArgs.folderTitle
//            }

//            when (items.size) {
//                1 -> {
//                    mainActivity.itemCountTextView.text = "${items.size} note"
//                }
//                else -> {
//                    mainActivity.itemCountTextView.text = "${items.size} notes"
//                }
//            }

            // todo: set controller itemEntityList as items
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}