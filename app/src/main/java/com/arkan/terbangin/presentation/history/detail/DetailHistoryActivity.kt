package com.arkan.terbangin.presentation.history.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.arkan.terbangin.R
import com.arkan.terbangin.base.OnItemCLickedListener
import com.arkan.terbangin.data.model.DetailHistory
import com.arkan.terbangin.data.model.History
import com.arkan.terbangin.databinding.ActivityCheckoutDetailBinding
import com.arkan.terbangin.databinding.ActivityDetailHistoryBinding
import com.arkan.terbangin.presentation.history.detail.adapter.DetailHistoryAdapter
import com.arkan.terbangin.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailHistoryActivity : AppCompatActivity() {
    private val binding: ActivityDetailHistoryBinding by lazy {
        ActivityDetailHistoryBinding.inflate(layoutInflater)
    }

    private lateinit var detailHistoryAdapter: DetailHistoryAdapter

    private val viewModel: DetailHistoryViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setAppBarTitle()
        setAdapter()
    }

    private fun onItemClick(item: DetailHistory) {

    }

    private fun setAdapter(){
        val itemClickListener = object : OnItemCLickedListener<DetailHistory> {
            override fun onItemClicked(item: DetailHistory) {
                onItemClick(item)
            }
        }
        detailHistoryAdapter = DetailHistoryAdapter(itemClickListener)
        binding.itemHistoryDetail.rvPassengerDetailsList.adapter = detailHistoryAdapter
    }

    private fun setAppBarTitle() {
        binding.layoutAppBar.tvAppbarTitle.text = getString(R.string.appbar_title_rincian_penerbangan)
    }

    companion object {
        const val EXTRA_ITEM_HISTORY = "EXTRA_ITEM_HISTORY"
        fun startActivity(
            context: Context,
            item: History
        ) {
            val intent = Intent(context, DetailHistoryActivity::class.java)
            intent.putExtra(EXTRA_ITEM_HISTORY, item)
            context.startActivity(intent)
        }
    }
}