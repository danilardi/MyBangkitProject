package com.dicoding.githubuser.ui.detailUser

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubuser.ui.ListUserAdapter
import com.dicoding.githubuser.core.data.source.remote.response.ItemsItem
import com.dicoding.githubuser.databinding.FragmentFollowBinding

class FollowFragment : Fragment() {
    companion object {
        const val TAG = "FollowerFragment"
        const val ARG_SECTION_NUMBER = "section_number"
        const val ARG_NAME = "app_name"
    }

    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FollowViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(FollowViewModel::class.java)

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvUser.layoutManager = layoutManager

        val username = arguments?.getString(ARG_NAME, "q")

        val index = arguments?.getInt(ARG_SECTION_NUMBER, 0)

        if (index == 1) {
            viewModel.getFollower(username.toString())
            viewModel.listFollower.observe(viewLifecycleOwner) { listFollower ->
                setUserData(listFollower)
            }
        } else {
            viewModel.getFollowing(username.toString())
            viewModel.listFollowing.observe(viewLifecycleOwner) { listFollowing ->
                setUserData(listFollowing)
            }

        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        viewModel.status.observe(viewLifecycleOwner) {
            if (it == false) {
                Toast.makeText(requireContext(), "Terjadi Kesalahan", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setUserData(userData: List<ItemsItem>) {
        val adapter = ListUserAdapter(userData)
        binding.rvUser.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}