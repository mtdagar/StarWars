package com.mtdagar.starwars.ui

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mtdagar.starwars.databinding.FilterOptionsLayoutBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mtdagar.starwars.R
import com.mtdagar.starwars.data.local.models.FilterOptions
import com.mtdagar.starwars.data.local.models.SortingOptions

class BottomSheetDialog(private val onSubmit: (FilterOptions) -> Unit) : BottomSheetDialogFragment() {

    private lateinit var binding: FilterOptionsLayoutBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FilterOptionsLayoutBinding.inflate(
            inflater,
            container,
            false
        )

        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        dialog?.setOnShowListener { it ->
            val d = it as BottomSheetDialog
            val bottomSheet =
                d.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.let {
                val behavior = BottomSheetBehavior.from(it)
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSubmit.setOnClickListener {
            when (binding.radioGroup1.checkedRadioButtonId) {
                R.id.radio_name -> {
                    onSubmit.invoke(
                        FilterOptions(SortingOptions.NAME)
                    )
                }
                R.id.radio_age -> {
                    onSubmit.invoke(
                        FilterOptions(SortingOptions.AGE)
                    )
                }
            }

            dismiss()
        }
    }

    companion object {
        const val TAG = "ModalBottomSheetDialog"
    }

}