package com.example.dicodingsubmissionawalfundamental.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dicodingsubmissionawalfundamental.data.remote.response.ItemsItem
import com.example.dicodingsubmissionawalfundamental.databinding.FragmentDetailFollowersFollowingUserBinding

class DetailFollowersFollowingUserFragment : Fragment() {
    private var _binding: FragmentDetailFollowersFollowingUserBinding? = null
    private val binding get() = _binding!!

    private var position = 0
    private var username = ""

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
            username = it.getString(ARG_USERNAME)!!
        }

        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvFollowingFollowers.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        binding.rvFollowingFollowers.addItemDecoration(itemDecoration)

        val detailUserViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[DetailUserViewModel::class.java]

        if (position == 1) {
            detailUserViewModel.findFollowersUser(username)
            detailUserViewModel.listUser.observe(viewLifecycleOwner) { userData ->
                setUserData(userData)
            }

            detailUserViewModel.isLoading.observe(viewLifecycleOwner) {
                showLoading(it)
            }

        } else {
            detailUserViewModel.findFollowingUser(username)
            detailUserViewModel.listUser.observe(viewLifecycleOwner) { userData ->
                setUserData(userData)
            }

            detailUserViewModel.isLoading.observe(viewLifecycleOwner) {
                showLoading(it)
            }
        }
    }

    private fun setUserData(userData: List<ItemsItem?>?) {
        val adapter = UserAdapter()
        adapter.submitList(userData)
        binding.rvFollowingFollowers.adapter = adapter
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }

    companion object {
        const val ARG_POSITION = "section_number"
        const val ARG_USERNAME = "charlesbintang"
    }
}