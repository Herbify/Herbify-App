package com.herbify.herbifyapp.ui.dashboard

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

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
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
        val layoutManagerHerbal = LinearLayoutManager(context)
        layoutManagerHerbal.orientation = LinearLayoutManager.HORIZONTAL

        val layoutManagerProduk = LinearLayoutManager(context)
        layoutManagerProduk.orientation = LinearLayoutManager.HORIZONTAL

        binding.rvHerbal.layoutManager = layoutManagerHerbal
        binding.rvHerbal.adapter = HerbalListAdapter()

        binding.rvProduk.layoutManager = layoutManagerProduk
        binding.rvProduk.adapter = HerbalListAdapter()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}