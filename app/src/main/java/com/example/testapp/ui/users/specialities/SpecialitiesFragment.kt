package com.example.testapp.ui.users.specialities

import com.example.testapp.R
import com.example.testapp.base.BaseFragment
import com.example.testapp.base.SharedActivityParent
import com.example.testapp.base.SharedFragmentChild
import com.example.testapp.data.network.response.UsersResponse
import com.example.testapp.databinding.FragmentSpecialitiesBinding
import com.example.testapp.ui.users.usersList.UsersFragment
import com.example.testapp.ui.users.usersList.UsersFragment.Companion.SPECIALITY_KEY
import com.example.testapp.tools.asScreenAnimated
import com.example.testapp.tools.setPaddingBottom
import com.example.testapp.tools.setPaddingTop

class SpecialitiesFragment(override val layoutId: Int = R.layout.fragment_specialities) :
    BaseFragment<SpecialitiesViewModel, FragmentSpecialitiesBinding>(), SharedFragmentChild {

    private val specialtiesAdapter: SpecialitiesAdapter = SpecialitiesAdapter(::onSpecialtyClick)


    override val sharedParentActivity: SharedActivityParent?
        get() = activity as? SharedActivityParent

    override fun observeViewModel() {
        viewModel.run {
            specialitiesLiveData.observe {
                handleSpecialties(it.data)
                if (it.isFromRemote) {
                    binding.swipeRefreshLayout.isRefreshing = false
                }
            }
            specialitiesError.observe { error ->
                binding.swipeRefreshLayout.isRefreshing = false
                showDefaultErrorDialog(error)
            }
        }
    }

    override fun initViews() {
        requestSpecialities()
        binding.run {
            specialitiesRv.adapter = specialtiesAdapter
            swipeRefreshLayout.setOnRefreshListener {
                requestSpecialities()
            }
        }
    }

    override fun applyInsetsPadding(
        systemStatusBarSize: Int,
        systemNavigationBarSize: Int,
        isKeyboardOpen: Boolean
    ) {
        binding.swipeRefreshLayout.setPaddingTop(systemStatusBarSize)
        binding.specialitiesRv.setPaddingBottom(systemNavigationBarSize)
    }

    private fun onSpecialtyClick(specialty: UsersResponse.User.Specialty) {
        sendDataToReceiver(UsersFragment::class.java, specialty, SPECIALITY_KEY)
        router.navigateTo(UsersFragment::class.java.asScreenAnimated(TransitionAnimation.HORISONTAL))
    }

    private fun handleSpecialties(list: List<UsersResponse.User.Specialty>) {
        specialtiesAdapter.setSpecialities(list)
    }

    private fun requestSpecialities() {
        binding.swipeRefreshLayout.isRefreshing = true
        viewModel.requestSpecialities()
    }
}