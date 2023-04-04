package com.ahmrh.githubuser.ui.user

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ahmrh.githubuser.api.UserItem
import com.ahmrh.githubuser.database.ListUserValue
import com.ahmrh.githubuser.databinding.FragmentFollowerBinding
import com.ahmrh.githubuser.ui.adapter.ListUserAdapter

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

        val users = ArrayList<ListUserValue>()
        for(user in listUser){
            users.add(ListUserValue(user.login, user.avatarUrl))
        }

        val adapter = ListUserAdapter(users)
        binding.rvUser.adapter = adapter

        adapter.setOnItemClickCallback(object: ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(user: ListUserValue) {
                showSelectedUser(user)
            }
        })
    }

    private fun showSelectedUser(user: ListUserValue) {
        val detailIntent = Intent(requireActivity(), UserActivity::class.java)
        detailIntent.putExtra(UserActivity.USERNAME, user.login)
        detailIntent.putExtra(UserActivity.AVATAR_URL, user.avatarUrl)
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