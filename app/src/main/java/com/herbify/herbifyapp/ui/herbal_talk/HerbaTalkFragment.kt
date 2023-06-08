package com.herbify.herbifyapp.ui.herbal_talk

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.herbify.herbifyapp.databinding.FragmentHerbaTalkBinding
import com.herbify.herbifyapp.ui.herbal_talk.add.AddNewPostActivity

class HerbaTalkFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentHerbaTalkBinding? = null


    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHerbaTalkBinding.inflate(inflater, container, false)
        return binding.root
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