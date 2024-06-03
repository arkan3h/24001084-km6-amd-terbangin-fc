package com.arkan.terbangin.presentation.home.class_sheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.arkan.terbangin.data.model.TicketClass
import com.arkan.terbangin.databinding.FragmentClassSheetBinding
import com.arkan.terbangin.presentation.home.class_sheet.adapter.ClassSheetAdapter
import com.arkan.terbangin.presentation.home.class_sheet.adapter.OnClassItemClickListener
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ClassSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentClassSheetBinding
    private lateinit var classSheetAdapter: ClassSheetAdapter
    private val classSheetViewModel: ClassSheetViewModel by viewModels()

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
        setFullScreen()
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupSaveButton()
        setupCloseButton()
    }

    private fun setFullScreen() {
        val bottomSheet: FrameLayout = dialog?.findViewById(com.google.android.material.R.id.design_bottom_sheet)!!
        bottomSheet.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
        val behavior = BottomSheetBehavior.from(bottomSheet)
        behavior.apply {
            state = BottomSheetBehavior.STATE_EXPANDED
            isDraggable = false
            addBottomSheetCallback(
                object : BottomSheetBehavior.BottomSheetCallback() {
                    override fun onStateChanged(
                        bottomSheet: View,
                        newState: Int,
                    ) {}

                    override fun onSlide(
                        bottomSheet: View,
                        slideOffset: Float,
                    ) {}
                },
            )
        }
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

    private fun setupSaveButton() {
        binding.btnSave.setOnClickListener {
            classSheetViewModel.selectedTicketClass.value?.let { selectedClass ->
                // Lakukan sesuatu dengan kelas tiket yang dipilih
                // Misalnya, tampilkan toast atau navigasi ke halaman lain
            }
        }
    }

    private fun setupCloseButton() {
        binding.ivCloseTab.setOnClickListener {
            dialog?.cancel()
        }
    }
}
