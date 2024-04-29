package com.example.dicodingsubmissionawalfundamental.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dicodingsubmissionawalfundamental.data.Result
import com.example.dicodingsubmissionawalfundamental.data.remote.response.ItemsItem
import com.example.dicodingsubmissionawalfundamental.databinding.FragmentDetailFollowersFollowingUserBinding

class DetailFollowersFollowingUserFragment : Fragment() {
    private var _binding: FragmentDetailFollowersFollowingUserBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding should not be accessed before onCreateView or after onDestroyView")

    private var position = 0
    private var username = ""

    private val detailUserViewModel by viewModels<DetailUserViewModel> {
        DetailUserViewModelFactory.getInstance(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailFollowersFollowingUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME).toString()
        }

        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvFollowingFollowers.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        binding.rvFollowingFollowers.addItemDecoration(itemDecoration)

        if (position == 1) {
            detailUserViewModel.findFollowersUser(username).observe(viewLifecycleOwner) { result ->
                if (result != null) {
                    when (result) {
                        is Result.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }

                        is Result.Success -> {
                            binding.progressBar.visibility = View.GONE
                            setUserData(result.data)
                        }

                        is Result.Error -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(
                                context,
                                "Terjadi kesalahan " + result.error,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
            detailUserViewModel.listUser.observe(viewLifecycleOwner) { userData ->
                setUserData(userData)
            }
        } else {
            detailUserViewModel.findFollowingUser(username).observe(viewLifecycleOwner) { result ->
                if (result != null) {
                    when (result) {
                        is Result.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }

                        is Result.Success -> {
                            binding.progressBar.visibility = View.GONE
                            setUserData(result.data)
                        }

                        is Result.Error -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(
                                context,
                                "Terjadi kesalahan " + result.error,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
            detailUserViewModel.listUser.observe(viewLifecycleOwner) { userData ->
                setUserData(userData)
            }
        }
    }

    private fun setUserData(userData: List<ItemsItem?>?) {
        val adapter = UserAdapter()
        adapter.submitList(userData)
        binding.rvFollowingFollowers.adapter = adapter
    }

    companion object {
        const val ARG_POSITION = "section_number"
        const val ARG_USERNAME = "charlesbintang"
    }
}