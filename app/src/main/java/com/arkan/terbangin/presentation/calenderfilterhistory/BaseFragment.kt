package com.arkan.terbangin.presentation.calenderfilterhistory

import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.arkan.terbangin.R

interface HasBackButton

abstract class BaseFragment(
    @LayoutRes layoutRes: Int,
) : Fragment(layoutRes) {
    abstract val titleRes: Int?

    override fun onStart() {
        super.onStart()
        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        if (this is HasBackButton) {
            actionBar?.title = if (titleRes != null) context?.getString(titleRes!!) else ""
            actionBar?.setDisplayHomeAsUpEnabled(true)
        } else {
            actionBar?.setDisplayHomeAsUpEnabled(false)
        }
    }

    override fun onStop() {
        super.onStop()

        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        if (this is HasBackButton) {
            actionBar?.title = context?.getString(R.string.text_calender_title_view)
        }
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
