package com.arkan.terbangin.presentation.class_sheet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.arkan.terbangin.data.model.TicketClass
import com.arkan.terbangin.databinding.FragmentClassSheetBinding
import com.arkan.terbangin.presentation.class_sheet.adapter.ClassSheetAdapter
class ClassSheetFragment : Fragment() {

    private lateinit var binding: FragmentClassSheetBinding
    private lateinit var classSheetAdapter: ClassSheetAdapter
    private val classSheetViewModel: ClassSheetViewModel by activityViewModels()

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
        savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
            //setupRecyclerView()
    }
/*
    private fun setupRecyclerView() {
        classSheetAdapter = ClassSheetAdapter(
            listOf(
                TicketClass("Economy", "IDR 200.000"),
                TicketClass("Premium Economy", "IDR 300.000"),
                TicketClass("Business", "IDR 500.000"),
                TicketClass("First Class", "IDR 800.000")
            ),
            object : OnClassItemClickListener {
                override fun onItemClick(ticketClass: TicketClass) {
                    // Simpan kelas tiket yang dipilih ke dalam ViewModel
                    classSheetViewModel.saveSelectedTicketClass(ticketClass)
                }
            }
        )
        binding.rvListClassSheet.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = classSheetAdapter
        }
    }*/
}
