package com.herbify.herbifyapp.ui.herbal_pedia

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.herbify.herbifyapp.databinding.FragmentHerbalpediaBinding
import com.herbify.herbifyapp.ui.ViewModelFactory
import com.herbify.herbifyapp.ui.adapter.HerbalListAdapter
import com.herbify.herbifyapp.ui.adapter.LoadingStateAdapter
import com.herbify.herbifyapp.ui.camera.CameraActivity

class HerbalPediaFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentHerbalpediaBinding? = null
    private lateinit var  viewModel: HerbalPediaViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(requireActivity(), ViewModelFactory(requireContext()))[HerbalPediaViewModel::class.java]

        _binding = FragmentHerbalpediaBinding.inflate(inflater, container, false)

        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBinding()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun initBinding() {
        binding.rvItemHerbalPedia.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.ivScan.setOnClickListener(this)
        setAdapter()
    }
    private fun setAdapter() {

        val adapter = HerbalListAdapter()
        binding.rvItemHerbalPedia.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter{
                adapter.retry()
            }
        )
        viewModel.herbals().observe(requireActivity()){
            adapter.submitData(lifecycle, it)
        }
    }

    override fun onClick(p0: View?) {
        when(p0){
            binding.ivScan ->{
                val intent = Intent(activity, CameraActivity::class.java)
                activity?.startActivity(intent)
            }
        }
    }
}