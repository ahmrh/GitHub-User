package com.ahmrh.githubuser

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ahmrh.githubuser.databinding.FragmentFollowerBinding

class FollowerFragment : Fragment() {
    private val viewModel by activityViewModels<UserViewModel>()

    private var _binding: FragmentFollowerBinding? = null
    private val binding get() = _binding!!

    private lateinit var listFollower: List<UserItem>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.listFollower.observe(viewLifecycleOwner) {
            setListUsers(it)
        }
        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        listFollower = viewModel.listFollower.value?: emptyList()
        setListUsers(listFollower)
    }

    private fun setListUsers(listUser: List<UserItem>) {
        binding.rvUser.layoutManager =  LinearLayoutManager(requireActivity())
        val adapter = ListUserAdapter(listUser)
        binding.rvUser.adapter = adapter

        adapter.setOnItemClickCallback(object: ListUserAdapter.OnItemClickCallback{
            override fun onItemClicked(user: UserItem) {
                showSelectedUser(user)
            }
        })
    }

    private fun showSelectedUser(user: UserItem) {
        val detailIntent = Intent(requireActivity(), UserActivity::class.java)
        detailIntent.putExtra(UserActivity.USERNAME, user.login)
        activity?.finish()
        startActivity(detailIntent)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }


}