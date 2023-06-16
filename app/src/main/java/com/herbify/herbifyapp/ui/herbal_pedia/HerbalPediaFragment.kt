package com.herbify.herbifyapp.ui.herbal_pedia

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.SearchView.OnQueryTextListener
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.herbify.herbifyapp.databinding.FragmentHerbalpediaBinding
import com.herbify.herbifyapp.ui.ViewModelFactory
import com.herbify.herbifyapp.ui.adapter.HerbalListAdapter
import com.herbify.herbifyapp.ui.adapter.LoadingStateAdapter
import com.herbify.herbifyapp.ui.camera.CameraActivity
import com.herbify.herbifyapp.ui.herbal_talk.add.AddNewPostActivity
import com.herbify.herbifyapp.utils.vibrate

class HerbalPediaFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentHerbalpediaBinding? = null
    private lateinit var  viewModel: HerbalPediaViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    companion object {
        private var REQUIRED_PERMISSIONS = arrayOf(android.Manifest.permission.CAMERA)
    }

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
        binding.brewComponent.rvBrewedItem.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        viewModel.brewedHerbal.observe(viewLifecycleOwner){ herbalList ->
            if(herbalList.isEmpty()){
                binding.brewComponent.brewComponent.visibility = View.GONE
            }else{
                binding.brewComponent.brewComponent.visibility = View.VISIBLE
                binding.brewComponent.rvBrewedItem.adapter = BrewAdapter(herbalList){data ->
                    viewModel.delete(data)
                    activity?.vibrate()
                }
            }
        }
        binding.ivScan.setOnClickListener(this)
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                val adapter = HerbalListAdapter()
                binding.rvItemHerbalPedia.adapter = adapter.withLoadStateFooter(
                    footer = LoadingStateAdapter{
                        adapter.retry()
                    }
                )
                viewModel.searchHerbal(binding.searchView.query.toString()).observe(requireActivity()){
                    adapter.submitData(lifecycle, it)
                }
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return true
            }

        })
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
                if(allPermissionsGranted()){
                    val intent = Intent(activity, CameraActivity::class.java)
                    activity?.startActivity(intent)
                }else{
                    activityResultLauncher.launch(REQUIRED_PERMISSIONS)
                }
            }
        }
    }

    private var activityResultLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ){result ->
        var areAllGranted = true
        for (b in result.values){
            areAllGranted = areAllGranted && b
        }

        if(areAllGranted){
            val intent = Intent(activity, CameraActivity::class.java)
            activity?.startActivity(intent)
        }
    }

    private fun allPermissionsGranted(): Boolean = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
    }
}