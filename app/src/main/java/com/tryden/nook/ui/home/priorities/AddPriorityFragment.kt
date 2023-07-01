package com.tryden.nook.ui.home.priorities

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.tryden.nook.R
import com.tryden.nook.database.entity.PriorityItemEntity
import com.tryden.nook.databinding.FragmentAddPriorityBinding
import com.tryden.nook.ui.BaseFragment
import com.tryden.nook.ui.BottomToolbarSetup
import com.tryden.nook.ui.convertLongToTime
import java.text.SimpleDateFormat
import java.util.*

class AddPriorityFragment : BaseFragment() {

    private var _binding: FragmentAddPriorityBinding? = null
    private val binding get() = _binding!!

    private val safeArgs: AddPriorityFragmentArgs by navArgs()
    private val selectedItemEntity: PriorityItemEntity? by lazy {
        sharedViewModel.priorityItemEntitiesLiveData.value?.find {
            it.id == safeArgs.selectedPriorityItemEntityId
        }
    }

    private var isEditMode: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAddPriorityBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tag = resources.getString(R.string.add_priority_fragment_key)
        Log.e("AddPriorityFragment()", "onViewCreated: $tag")

        // Setup Bottom Toolbar
        BottomToolbarSetup(
            fragmentKey = tag,
            activity = mainActivity,
        ).bottomToolbarSetup()

        binding.saveButton.setOnClickListener {
            savePriorityItemToDatabase()
        }

        sharedViewModel.transactionCompleteLiveData.observe(viewLifecycleOwner) { complete ->
            if (complete) {

                if (isEditMode) {
                    navigateUp()
                    return@observe
                }
                Toast.makeText(
                    requireActivity(), getString(R.string.item_saved), Toast.LENGTH_SHORT
                ).show()

                binding.titleEditText.text = null
                binding.descriptionEditText.text = null
                binding.titleEditText.requestFocus()
                mainActivity.showKeyboard()
                binding.radioGroup.check(R.id.radioButtonLow)

            }
        }
        // Show keyboard and default select our Title EditText
        mainActivity.showKeyboard()
        binding.titleEditText.requestFocus()

        updateIfInEditMode()
    }

    private fun updateIfInEditMode() {
        // Setup screen if in edit mode
        selectedItemEntity?.let { itemEntity ->
            isEditMode = true

            binding.titleEditText.setText(itemEntity.title)
            binding.titleEditText.setSelection(itemEntity.title.length)
            binding.descriptionEditText.setText(itemEntity.description)
            when (itemEntity.priority) {
                1 -> binding.radioGroup.check(R.id.radioButtonLow)
                2 -> binding.radioGroup.check(R.id.radioButtonMedium)
                else -> binding.radioGroup.check(R.id.radioButtonHigh)
            }
            binding.saveButton.text = getString(R.string.update)
            mainActivity.supportActionBar?.title = getString(R.string.update_item)
        }
    }

    private fun savePriorityItemToDatabase() {
        val itemTitle = binding.titleEditText.text.toString().trim()
        if (itemTitle.isEmpty()) {
            binding.titleTextField.error = getString(R.string.required_field)
            return
        }

        binding.titleTextField.error = null

        val description = binding.descriptionEditText.text.toString().trim()
        val priority = when(binding.radioGroup.checkedRadioButtonId) {
            R.id.radioButtonLow -> 1
            R.id.radioButtonMedium -> 2
            R.id.radioButtonHigh -> 3
            else -> 0
        }

        if (isEditMode) {
            val itemEntity = selectedItemEntity!!.copy(
                title = itemTitle,
                description = description,
                priority = priority
            )

            sharedViewModel.updatePriorityItem(itemEntity)
            return
        }

        val itemEntity = PriorityItemEntity(
            id = UUID.randomUUID().toString(),
            title = itemTitle,
            description = description,
            priority = priority,
            lastModified = convertLongToTime(System.currentTimeMillis()), // todo fix
            categoryId = "" // todo update later when we have categories in the app
        )
        sharedViewModel.insertPriorityItem(itemEntity)
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