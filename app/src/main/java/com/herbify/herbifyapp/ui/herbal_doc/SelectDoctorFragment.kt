package com.herbify.herbifyapp.ui.herbal_doc

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.herbify.herbifyapp.R
import com.herbify.herbifyapp.databinding.FragmentSelectDoctorBinding
import com.herbify.herbifyapp.ui.ViewModelFactory
import com.herbify.herbifyapp.ui.adapter.DoctorListAdapter
import com.herbify.herbifyapp.utils.RepositoryResult

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SelectDoctorFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SelectDoctorFragment : Fragment() {
    private lateinit var binding : FragmentSelectDoctorBinding
    private lateinit var viewModel : DoctorViewModel
    private lateinit var adapter : DoctorListAdapter

    companion object{
        const val ARGS_DOCTOR_TYPE = "doctor_type"
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSelectDoctorBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = DoctorListAdapter()
        initViewModel()
        initBinding()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this, ViewModelFactory(context))[DoctorViewModel::class.java]
        val type = arguments?.getInt(ARGS_DOCTOR_TYPE)
            viewModel.doctor.observe(viewLifecycleOwner){result ->
                if(result is RepositoryResult.Success) {
                    if(type == 0){
                        adapter.submitList(result.data)
                    }else{
                        adapter.submitList(result.data.filter { it.status == 1 })
                    }
                }
            }

    }

    private fun initBinding() {
        binding.rvDoctorList.layoutManager = LinearLayoutManager(context)
        binding.rvDoctorList.setHasFixedSize(true)
        binding.rvDoctorList.adapter = adapter
    }
}