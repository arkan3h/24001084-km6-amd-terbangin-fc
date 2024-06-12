package com.arkan.terbangin.presentation.home.class_sheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.arkan.terbangin.data.model.TicketClass
import com.arkan.terbangin.databinding.FragmentClassSheetBinding
import com.arkan.terbangin.presentation.home.class_sheet.adapter.ClassSheetAdapter
import com.arkan.terbangin.presentation.home.class_sheet.adapter.OnClassItemClickListener
import com.arkan.terbangin.presentation.home.common.SaveButtonClickListener
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class ClassSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentClassSheetBinding
    private lateinit var classSheetAdapter: ClassSheetAdapter
    private val classSheetViewModel: ClassSheetViewModel by viewModel(ownerProducer = { requireParentFragment() })
    var listener: SaveButtonClickListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentClassSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setClickListener()
    }

    private fun setupRecyclerView() {
        classSheetAdapter =
            ClassSheetAdapter(
                listOf(
                    TicketClass("Economy", "IDR 200.000"),
                    TicketClass("Premium Economy", "IDR 300.000"),
                    TicketClass("Business", "IDR 500.000"),
                    TicketClass("First Class", "IDR 800.000"),
                ),
                object : OnClassItemClickListener {
                    override fun onItemClick(ticketClass: TicketClass) {
                        // Simpan kelas tiket yang dipilih ke dalam ViewModel
                        classSheetViewModel.saveSelectedTicketClass(ticketClass)
                    }
                },
            )
        binding.rvListClassSheet.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = classSheetAdapter
        }
    }

    private fun setClickListener() {
        binding.btnSave.setOnClickListener {
            classSheetViewModel.selectedTicketClass.value?.let { selectedClass ->
                listener?.onClassSelected(selectedClass)
                dialog?.dismiss()
            }
        }
        binding.ivCloseTab.setOnClickListener {
            dialog?.cancel()
        }
    }
}
