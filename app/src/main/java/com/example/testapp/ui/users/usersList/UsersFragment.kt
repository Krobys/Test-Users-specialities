package com.example.testapp.ui.users.usersList

import com.example.testapp.R
import com.example.testapp.base.BaseFragment
import com.example.testapp.base.SharedActivityParent
import com.example.testapp.base.SharedFragmentChild
import com.example.testapp.data.network.response.UsersResponse
import com.example.testapp.databinding.FragmentUsersBinding
import com.example.testapp.ui.MainActivity
import com.example.testapp.ui.users.detailedUser.DetailedUserFragment
import com.example.testapp.ui.users.detailedUser.DetailedUserFragment.Companion.USER_KEY
import com.example.testapp.tools.asScreenAnimated
import com.example.testapp.tools.setPaddingBottom

class UsersFragment(override val layoutId: Int = R.layout.fragment_users) :
    BaseFragment<UsersViewModel, FragmentUsersBinding>(), SharedFragmentChild {

    private val usersAdapter = UsersAdapter(::onUserClick)

    override val sharedParentActivity: SharedActivityParent?
        get() = activity as? MainActivity

    override fun observeViewModel() {
        initReceiver(viewLifecycleOwner)
        viewModel.run {
            usersLiveData.observe {
                usersAdapter.setUsers(it)
            }
        }
    }

    override fun initViews() {
        viewModel.userSpecialty?.let {
            binding.title.text = it.name
        }
        binding.run {
            usersRv.adapter = usersAdapter
        }
    }

    override fun applyInsetsPadding(
        systemStatusBarSize: Int,
        systemNavigationBarSize: Int,
        isKeyboardOpen: Boolean
    ) {
        binding.run {
            title.setPadding(0, systemStatusBarSize, 0, 0)
            usersRv.setPaddingBottom(systemNavigationBarSize)
        }
    }

    private fun onUserClick(user: UsersResponse.User) {
        sendDataToReceiver(DetailedUserFragment::class.java, user, USER_KEY)
        router.navigateTo(DetailedUserFragment::class.java.asScreenAnimated(TransitionAnimation.HORISONTAL))
    }

    private fun requestUsersForSpeciality(specialty: UsersResponse.User.Specialty) {
        viewModel.getUsersForSpeciality(specialty.specialtyId)
    }

    override fun sharedDataReceived(data: Any, identifier: String) {
        if (data is UsersResponse.User.Specialty && identifier == SPECIALITY_KEY) {
            viewModel.userSpecialty = data
            binding.title.text = data.name
            requestUsersForSpeciality(data)
        }
    }

    companion object {
        const val SPECIALITY_KEY = "SPECIALITY_KEY"
    }
}