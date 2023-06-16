package com.herbify.herbifyapp.ui

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.herbify.herbifyapp.databinding.FragmentPopUpCameraBinding
import com.herbify.herbifyapp.ui.herbal_pedia.HerbalPediaDetailActivity
import com.herbify.herbifyapp.ui.herbal_pedia.HerbalPediaViewModel
import java.io.File

class PopUpCameraFragment : DialogFragment() {

    private lateinit var binding: FragmentPopUpCameraBinding

    companion object {
        private const val ARG_PHOTO_FILE = "photo_file"
        private const val ARG_PREDICTED_LABEL = "predicted_label"

        fun newInstance(photoFile: File, predictedLabel: String): PopUpCameraFragment {
            val fragment = PopUpCameraFragment()
            val args = Bundle().apply {
                putSerializable(ARG_PHOTO_FILE, photoFile)
                putString(ARG_PREDICTED_LABEL, predictedLabel)
            }
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        @Suppress("DEPRECATION")
        val photoFile = arguments?.getSerializable(ARG_PHOTO_FILE) as? File
        val predictedLabel = arguments?.getString(ARG_PREDICTED_LABEL) ?: ""

        binding = FragmentPopUpCameraBinding.inflate(layoutInflater)
        val view = binding.root

        binding.ivGambarHerbal.setImageURI(Uri.fromFile(photoFile))
        binding.tvNamaHerbal.text = predictedLabel
        binding.btnDetail.setOnClickListener {
            val intent = Intent(requireActivity(), HerbalPediaDetailActivity::class.java)
            intent.putExtra(HerbalPediaDetailActivity.SCANNED_HERBAL, predictedLabel)
            requireActivity().startActivity(intent)
        }

        return AlertDialog.Builder(requireActivity())
            .setView(view)
            .setTitle("Camera Result")
            .create()
    }
}
