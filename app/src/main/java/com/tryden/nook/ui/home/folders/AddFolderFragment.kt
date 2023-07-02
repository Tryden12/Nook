package com.tryden.nook.ui.home.folders

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.tryden.nook.R
import com.tryden.nook.databinding.FragmentAddFolderBinding
import com.tryden.nook.ui.BaseFragment
import com.tryden.nook.ui.BottomToolbarSetup


class AddFolderFragment : BaseFragment() {

    private var _binding: FragmentAddFolderBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAddFolderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tag = resources.getString(R.string.add_folder_fragment_key)
        Log.e("AddFolderFragment()", "onViewCreated: $tag")

        mainActivity.supportActionBar?.hide()

        // Setup Bottom Toolbar
        BottomToolbarSetup(
            fragmentKey = tag,
            activity = mainActivity,
        ).bottomToolbarSetup()

        // Show keyboard and default select our Title EditText
        mainActivity.showKeyboard()
        binding.titleEditText.requestFocus()
    }

    override fun onPause() {
        super.onPause()

        sharedViewModel.transactionCompleteLiveData.postValue(false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}