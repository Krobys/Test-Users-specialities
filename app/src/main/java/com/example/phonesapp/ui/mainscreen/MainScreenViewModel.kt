package com.example.phonesapp.ui.mainscreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.phonesapp.PhoneListApp
import com.example.phonesapp.base.BaseViewModel
import com.example.phonesapp.network.ApiRetrofitInterface
import com.example.phonesapp.network.PersonInfoRandFull
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainScreenViewModel @Inject constructor(
    application: PhoneListApp,
    var retrofitInterface: ApiRetrofitInterface): BaseViewModel(application){

    private var personLiveData: MutableLiveData<List<PersonInfoRandFull.Result>> = MutableLiveData()

    public fun getRandomPersonContacts(): LiveData<List<PersonInfoRandFull.Result>>{
        disposable += retrofitInterface.getPersonsList(20)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError {
                Log.d("test", it.message)
            }
            .subscribe{ personList ->
                personLiveData.value = personList.results
            }
        return personLiveData
    }
}