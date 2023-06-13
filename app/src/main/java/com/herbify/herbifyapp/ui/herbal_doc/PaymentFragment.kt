package com.herbify.herbifyapp.ui.herbal_doc

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.herbify.herbifyapp.R
import com.herbify.herbifyapp.databinding.FragmentPaymentBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PaymentFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PaymentFragment : Fragment() {
    private lateinit var binding: FragmentPaymentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentPaymentBinding.inflate(layoutInflater, container,false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val doctorId = arguments?.getInt("doctor_id")
        val doctorName = arguments?.getString("doctor_name")
        binding.btnPayment.setOnClickListener {
            val intent = Intent(activity, DoctorChatActivity::class.java)
            intent.putExtra("doctor_id", doctorId)
            intent.putExtra("doctor_name", doctorName)
            activity?.startActivity(intent)
        }
    }

}