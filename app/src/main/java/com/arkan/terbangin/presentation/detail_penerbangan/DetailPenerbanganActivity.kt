package com.arkan.terbangin.presentation.detail_penerbangan

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.arkan.terbangin.R
import com.arkan.terbangin.databinding.ActivityDetailPenerbanganBinding

class DetailPenerbanganActivity : AppCompatActivity() {
    private val binding: ActivityDetailPenerbanganBinding by lazy {
        ActivityDetailPenerbanganBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setAppBarTitle()
        setClickListener()
    }

    private fun setAppBarTitle() {
        binding.layoutAppBar.tvAppbarTitle.text = getString(R.string.appbar_title_rincian_penerbangan)
    }

    private fun setClickListener() {
        binding.layoutTotalPrice.btnChoose.setOnClickListener {
            // if(tiket == habis)
            // TicketSoldOutBottomSheet().show(supportFragmentManager, null)
            // else
            // TODO
        }
    }
}
