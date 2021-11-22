package com.example.testapp.ui.users.usersList

import com.example.testapp.base.BaseViewModel
import com.example.testapp.base.SingleLiveEvent
import com.example.testapp.data.TestRepository
import com.example.testapp.data.network.response.UsersResponse
import javax.inject.Inject

class UsersViewModel @Inject constructor(private val testRepository: TestRepository) :
    BaseViewModel() {

    val usersLiveData: SingleLiveEvent<List<UsersResponse.User>> = SingleLiveEvent()
    var userSpecialty: UsersResponse.User.Specialty? = null

    fun getUsersForSpeciality(specialityId: Int) {
        launchCoroutineScope {
            testRepository.requestUsersList {
                it.fold(ifLeft = { _ ->
                    //do nothing
                }, ifRight = { dataWrapper ->
                    val users =
                        dataWrapper.data.response.filter { user -> user.specialty.any { specialty -> specialty.specialtyId == specialityId } }
                    usersLiveData.postValue(users)
                })
            }
        }
    }
}