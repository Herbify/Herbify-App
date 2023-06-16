package com.herbify.herbifyapp.ui.dashboard

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.google.android.material.tabs.TabLayoutMediator
import com.herbify.herbifyapp.databinding.FragmentDashboardBinding
import com.herbify.herbifyapp.ui.ViewModelFactory
import com.herbify.herbifyapp.ui.adapter.CarouselRVAdapter
import com.herbify.herbifyapp.ui.adapter.HerbalListAdapter
import com.herbify.herbifyapp.ui.camera.CameraActivity
import com.herbify.herbifyapp.ui.herbal_doc.QnaChatActivity
import com.herbify.herbifyapp.ui.herbal_doc.SelectDoctorActivity
import com.herbify.herbifyapp.utils.RepositoryResult
import kotlin.math.abs

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

        binding.rvCarousel.apply {
            clipChildren = false  // No clipping the left and right items
            clipToPadding = false  // Show the viewpager in full width without clipping the padding
            offscreenPageLimit = 3  // Render the left and right items
            (getChildAt(0) as RecyclerView).overScrollMode =
                RecyclerView.OVER_SCROLL_NEVER // Remove the scroll effect
        }

        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer((40 * Resources.getSystem().displayMetrics.density).toInt()))
        compositePageTransformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = (0.80f + r * 0.20f)
        }
        binding.rvCarousel.setPageTransformer(compositePageTransformer)

        viewModel.topArticle().observe(requireActivity()){result ->
            when(result){
                is RepositoryResult.Success -> {
                    binding.rvCarousel.adapter = CarouselRVAdapter(result.data)
                    TabLayoutMediator(binding.carouselTabLayout, binding.rvCarousel){tab, position ->

                    }.attach()
                }
                is RepositoryResult.Loading -> {}
                is RepositoryResult.Error -> {}
            }
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