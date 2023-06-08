package com.herbify.herbifyapp.ui.herbal_doc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.herbify.herbifyapp.R
import com.herbify.herbifyapp.databinding.ActivitySelectDoctorBinding
import com.herbify.herbifyapp.databinding.FragmentSelectDoctorBinding
import com.herbify.herbifyapp.ui.ViewModelFactory
import com.herbify.herbifyapp.ui.adapter.DoctorListAdapter
import com.herbify.herbifyapp.utils.RepositoryResult

class SelectDoctorActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySelectDoctorBinding
    lateinit var viewPager: ViewPager2

    companion object{
        val TAB_TITLES = intArrayOf(
            R.string.all_doctor,
            R.string.dokter_exist
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectDoctorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sectionsPagerAdapter = SectionsPagerAdapter(this)

        viewPager = binding.vpDokter
        viewPager.adapter = sectionsPagerAdapter

        val tabs : TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager){tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()


        supportActionBar?.hide()
        binding.btnBack.setOnClickListener {
            @Suppress("DEPRECATION")
            onBackPressed()
        }

    }
}