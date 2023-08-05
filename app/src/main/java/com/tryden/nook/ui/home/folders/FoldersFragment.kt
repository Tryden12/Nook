package com.tryden.nook.ui.home.folders

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.tryden.nook.R
import com.tryden.nook.database.entity.FolderEntity
import com.tryden.nook.databinding.FragmentFoldersBinding
import com.tryden.nook.ui.BaseFragment
import com.tryden.nook.ui.BottomToolbarSetup
import com.tryden.nook.ui.home.OnFolderSelectedInterface
import com.tryden.nook.ui.home.bottomsheet.AddItemSheet
import com.tryden.nook.ui.home.checklists.checklist.ChecklistFragmentArgs


class FoldersFragment : BaseFragment(), OnFolderSelectedInterface {

    private var _binding: FragmentFoldersBinding? = null
    private val binding get() = _binding!!

    /**
     * The safeArgs here holds the section title string value needed to
     * display the correct section title: Priorities, Checklists, Notes
     *
     * This is passed in from HomeFragment() NavDirections.
     */
    private val safeArgs: FoldersFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFoldersBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tag = resources.getString(R.string.all_folders_fragment_key)
        Log.d(tag, "onViewCreated: $tag")

        // Support action bar title
        mainActivity.supportActionBar?.title = getString(R.string.folders_title)

        // Setup Bottom Toolbar
        setupBottomToolbar()

        // Setup Epoxy Controller, but don't build models just yet
        val controller = AllFoldersEpoxyController(safeArgs.folderType, this)
        binding.epoxyRecyclerView.setController(controller)

        sharedViewModel.foldersLiveData.observe(viewLifecycleOwner) { folders ->
            val itemEntityList = folders.filter {
                it.type == safeArgs.folderType
            }
            // Set folderEntitiesList to requestModelBuild()
            controller.folderEntitiesList = itemEntityList as ArrayList<FolderEntity>
        }
    }

    private fun setupBottomToolbar() {
        // Show correct toolbar, hide others
        mainActivity.bottomToolbarHome.isVisible = true
        mainActivity.bottomToolbarItemsList.isInvisible = true
        mainActivity.bottomToolbarEditItem.isInvisible = true

        // OnClick listeners
        mainActivity.addFolderImageView.setOnClickListener {
            AddItemSheet().show(mainActivity.supportFragmentManager, null)
        }
        mainActivity.addItemImageViewHome.isGone = true // todo: revisit
    }

    override fun onFolderSelected(selectedFolder: FolderEntity) {
        // Update current folder selected
        // Figure out which fragment to navigate to
        sharedViewModel.updateCurrentFolderSelected(selectedFolder)
        when (selectedFolder.type) {
            /** Priority **/
            getString(R.string.priority) -> {
                Log.d(tag, "onFolderSelected: [Priority], ${selectedFolder.title}")
                val navDirections = FoldersFragmentDirections.actionFoldersFragmentToPrioritiesFragment(
                    folderTitle = selectedFolder.title
                )
                navigateViewNavGraph(navDirections)
            }
            /** Checklist **/
            getString(R.string.checklist) -> {
                Log.d(tag, "onFolderSelected: [Checklist], ${selectedFolder.title}")
                val navDirections = FoldersFragmentDirections.actionFoldersFragmentToChecklistFragment(
                    folderTitle = selectedFolder.title
                )
                navigateViewNavGraph(navDirections)
            }
            /** Note **/
            getString(R.string.note) -> {
                Log.d(tag, "onFolderSelected: [Note], ${selectedFolder.title}")
                val navDirections = FoldersFragmentDirections.actionFoldersFragmentToNotesListFragment(
                    folderTitle = selectedFolder.title
                )
                navigateViewNavGraph(navDirections)
            }
            else -> {
                // Do nothing
                Log.e(tag, "onFolderSelected: ELSE case, no selectedFolder type not found")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}