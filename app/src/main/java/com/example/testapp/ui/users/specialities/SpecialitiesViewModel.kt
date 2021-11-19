package com.example.testapp.ui.users.specialities

import com.example.testapp.base.BaseViewModel
import com.example.testapp.base.SingleLiveEvent
import com.example.testapp.data.TestRepository
import com.example.testapp.data.network.DataWrapper
import com.example.testapp.data.network.response.Error
import com.example.testapp.data.network.response.UsersResponse
import javax.inject.Inject

class SpecialitiesViewModel @Inject constructor(
    private val testRepository: TestRepository
) : BaseViewModel() {

    val specialitiesLiveData: SingleLiveEvent<DataWrapper<List<UsersResponse.User.Specialty>>> = SingleLiveEvent()
    val specialitiesError: SingleLiveEvent<Error> = SingleLiveEvent()

    fun requestSpecialities(){
        launchCoroutineScope {
            testRepository.requestUsersList {
                it.fold(ifLeft = { error ->
                    specialitiesError.postValue(error)
                }, ifRight = { dataResponseWrapper ->
                    val specialties = dataResponseWrapper.data.response.convertToSpecialties()
                    specialitiesLiveData.postValue(DataWrapper(dataResponseWrapper.isFromRemote, specialties.toList()))
                })
            }
        }
    }

    private suspend fun List<UsersResponse.User>.convertToSpecialties(): Set<UsersResponse.User.Specialty> {
        return flatMap { it.specialty.asIterable() }.toSet()
    }
}