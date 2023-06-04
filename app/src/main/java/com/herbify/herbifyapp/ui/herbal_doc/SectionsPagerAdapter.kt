package com.herbify.herbifyapp.ui.herbal_doc

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        TODO("Not yet implemented")
//        val fragment = SelectDoctorFragment()
//        fragment.arguments = Bundle().apply {
//            putInt(SelectDoctorFragment.ARG_POSITION, position + 1)
//        }
    }


}