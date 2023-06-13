package com.herbify.herbifyapp.ui.herbal_doc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.add
import com.herbify.herbifyapp.R
import com.herbify.herbifyapp.databinding.ActivityDetailDoctorBinding
import com.herbify.herbifyapp.model.Doctor

class DetailDoctorActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailDoctorBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailDoctorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBinding()
    }

    private fun initBinding() {
        val doctorData : Doctor? = intent.getParcelableExtra("doctor")
        binding.tvNameUser.text = doctorData?.name

        val fragmentManager = supportFragmentManager
        val paymentFragment = PaymentFragment()

        val bundle = Bundle()
        bundle.putString("doctor_name", doctorData?.name)
        bundle.putInt("doctor_id", doctorData?.id!!)
        paymentFragment.arguments = bundle

        binding.btnConsultNow.setOnClickListener {
            fragmentManager.beginTransaction().apply {
                add(binding.framePayment.id, paymentFragment, PaymentFragment::class.java.simpleName)
                addToBackStack(null)
                commit()
            }
        }
        supportActionBar?.hide()
    }
}