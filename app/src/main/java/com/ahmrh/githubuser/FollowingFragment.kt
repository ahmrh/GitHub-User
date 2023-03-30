package com.ahmrh.githubuser

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ahmrh.githubuser.databinding.FragmentFollowerBinding

class FollowingFragment: Fragment() {
    private val viewModel by activityViewModels<UserViewModel>()

    private var _binding: FragmentFollowerBinding? = null
    private val binding get() = _binding!!

    private lateinit var listFollowing : List<UserItem>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.listFollowing.observe(viewLifecycleOwner) {
            setListUsers(it)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        listFollowing = viewModel.listFollowing.value?: emptyList()
        setListUsers(listFollowing)
    }

    private fun setListUsers(listUser : List<UserItem>){
        binding.rvUser.layoutManager = LinearLayoutManager(requireActivity())
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