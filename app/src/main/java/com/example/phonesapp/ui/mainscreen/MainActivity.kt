package com.example.phonesapp.ui.mainscreen

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.phonesapp.R
import com.example.phonesapp.base.BaseActivity
import com.example.phonesapp.databinding.ActivityMainBinding
import com.example.phonesapp.network.PersonInfoRandFull
import com.example.phonesapp.ui.detailedscreen.DetailedPersonInfo
import com.example.phonesapp.ui.detailedscreen.DetailedPersonScreenActivity

class MainActivity(override val layoutId: Int = R.layout.activity_main,
                   override val viewModelClass: Class<MainScreenViewModel> = MainScreenViewModel::class.java
) : BaseActivity<MainScreenViewModel, ActivityMainBinding>(), OnPersonClickListener {

    private var personAdapter = PersonAdapter(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle(R.string.home)
        binding.personsRecView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = personAdapter
        }
        updatePersonsContacts()
    }

    override fun onPersonClick(personInfo: PersonInfoRandFull.Result?, transView: View) {
        personInfo?.let {
            val detailedPersonInfo = DetailedPersonInfo(
                it.picture.medium,
                it.name.first,
                it.name.last,
                it.dob.age,
                it.phone,
                it.email,
                it.location.timezone.description)
            DetailedPersonScreenActivity.startActivity(this, detailedPersonInfo, transView)
        }
    }

    private fun updatePersonsContacts(){
        binding.apply {
            personsRecView.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
        }
        viewModel.getRandomPersonContacts().observe(this, Observer {personsRandomList ->
            personAdapter.personsList = ArrayList(personsRandomList)
            binding.apply {
                personsRecView.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
            }
        })
    }
}

interface OnPersonClickListener{
   fun onPersonClick(personInfo: PersonInfoRandFull.Result?, transView: View)
}