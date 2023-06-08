package com.herbify.herbifyapp.ui.herbal_doc

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.herbify.herbifyapp.data.dummies.DummyDoctor
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

    companion object{
        const val ARGS_DOCTOR_TYPE = "doctor_type"
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSelectDoctorBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBinding()
        initViewModel()
    }

    private fun initViewModel() {
        val doctorAdapter = DoctorListAdapter()

        viewModel = ViewModelProvider(requireActivity(), ViewModelFactory(context))[DoctorViewModel::class.java]
        val type = arguments?.getInt(ARGS_DOCTOR_TYPE)
        viewModel.doctor.observe(viewLifecycleOwner){result ->
            if(result != null){
                Log.d("Select DoctorFragment", result.toString())
                Log.d("Select DoctorFragment", type.toString())
                when(result){
                    is RepositoryResult.Success -> {
                        Log.d("Select DoctorFragment", result.data.toString())
                        doctorAdapter.submitList(result.data)
                        binding.rvDoctorList.apply {
                            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                            adapter = doctorAdapter
                        }
                    }
                    else -> {}
                }
            }
        }
    }

    private fun initBinding() {

    }
}