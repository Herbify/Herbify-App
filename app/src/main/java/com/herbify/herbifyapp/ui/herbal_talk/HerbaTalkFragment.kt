package com.herbify.herbifyapp.ui.herbal_talk

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.herbify.herbifyapp.databinding.FragmentHerbaTalkBinding
import com.herbify.herbifyapp.ui.ViewModelFactory
import com.herbify.herbifyapp.ui.adapter.ArticleAdapter
import com.herbify.herbifyapp.ui.herbal_talk.add.AddNewPostActivity
import com.herbify.herbifyapp.utils.RepositoryResult

class HerbaTalkFragment : Fragment(), View.OnClickListener {

    private lateinit var _binding: FragmentHerbaTalkBinding
    private lateinit var viewModel: HerbalTalkViewModel

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this, ViewModelFactory(requireContext()))[HerbalTalkViewModel::class.java]
        _binding = FragmentHerbaTalkBinding.inflate(inflater, container, false)
        binding.btnAdd.setOnClickListener(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvArticle.layoutManager = LinearLayoutManager(requireContext())
        viewModel.result.observe(requireActivity()){result ->
            when(result){
                is RepositoryResult.Success -> {
                    binding.rvArticle.adapter = ArticleAdapter(result.data, listOf())
                }
                is RepositoryResult.Loading -> {

                }
                is RepositoryResult.Error -> {
                    Toast.makeText(requireContext(), result.error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onClick(p0: View) {
        when (p0){
            binding.btnAdd -> {
                val intent = Intent(activity, AddNewPostActivity::class.java)
                activity?.startActivity(intent)
            }
        }
    }
}