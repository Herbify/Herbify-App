package com.herbify.herbifyapp.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.herbify.herbifyapp.databinding.FragmentDashboardBinding
import com.herbify.herbifyapp.ui.ViewModelFactory
import com.herbify.herbifyapp.ui.adapter.HerbalListAdapter
import com.herbify.herbifyapp.ui.camera.CameraActivity
import com.herbify.herbifyapp.ui.herbal_doc.QnaChatActivity
import com.herbify.herbifyapp.ui.herbal_doc.SelectDoctorActivity

class DashboardFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentDashboardBinding? = null
    private lateinit var viewModel: DashboardViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel =
            ViewModelProvider(
                this,
                ViewModelFactory(requireContext())
            )[DashboardViewModel::class.java]

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBinding()
    }

    fun initBinding(){
        binding.rvHerbal.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        val adapter = HerbalListAdapter()
        viewModel.herbals().observe(requireActivity()){
            adapter.submitData(lifecycle, it)
        }
        binding.rvHerbal.adapter = adapter

        binding.btnChatDoc.setOnClickListener(this)
        binding.btnChat.setOnClickListener(this)
        binding.btnScan.setOnClickListener(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(p0: View?) {
        when(p0){
            binding.btnChatDoc -> {
                val intent = Intent(activity, SelectDoctorActivity::class.java)
                activity?.startActivity(intent)
            }
            binding.btnScan -> {
                val intent = Intent(activity, CameraActivity::class.java)
                activity?.startActivity(intent)
            }
            binding.btnChat -> {
                val intent = Intent(activity, QnaChatActivity::class.java)
                activity?.startActivity(intent)
            }
        }
    }
}