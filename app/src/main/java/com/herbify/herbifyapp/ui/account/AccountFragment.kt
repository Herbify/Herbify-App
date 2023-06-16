package com.herbify.herbifyapp.ui.account

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.herbify.herbifyapp.databinding.FragmentAccountBinding
import com.herbify.herbifyapp.ui.ViewModelFactory
import com.herbify.herbifyapp.ui.auth.login.LoginActivity
import com.herbify.herbifyapp.utils.createFile
import java.io.FileOutputStream

class AccountFragment : Fragment(), OnClickListener {

    private var _binding: FragmentAccountBinding? = null
    private lateinit var viewModel: AccountViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
         viewModel =
            ViewModelProvider(requireActivity(), ViewModelFactory(requireContext()))[AccountViewModel::class.java]

        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        initBinding()
        return binding.root
    }

    private fun initBinding() {
        binding.btnLogout.setOnClickListener(this)
        binding.tvNameUser.text = viewModel.user.name
        binding.tvAddress.text = viewModel.user.email
        binding.ivAccountPhoto.setOnClickListener {
            val pickPhoto = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
            startActivityForResult(pickPhoto, 1) //one can be replaced with any action code
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            val selectedImage: Uri = data?.data!!
            binding.ivAccountPhoto.setImageURI(selectedImage)

            val photoFile = createFile(activity?.application!!)
            val inputStream = requireActivity().contentResolver.openInputStream(selectedImage)
            inputStream?.use { input ->
                val outputStream = FileOutputStream(photoFile)
                outputStream.use { output ->
                    input.copyTo(output)
                }
            }

            viewModel.updatePhoto(photoFile)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBinding()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(p0: View?) {
        when(p0!!){
            binding.btnLogout -> {
                viewModel.logout()
                val intent = Intent(activity, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                activity?.startActivity(intent)
                activity?.finish()
            }
        }
    }
}