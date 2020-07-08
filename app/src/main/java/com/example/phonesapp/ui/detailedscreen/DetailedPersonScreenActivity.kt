package com.example.phonesapp.ui.detailedscreen

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import com.example.phonesapp.R
import com.example.phonesapp.base.BaseActivityWithoutViewModel
import com.example.phonesapp.databinding.ActivityDetailedPersonScreenBinding


class DetailedPersonScreenActivity(override val layoutId: Int = R.layout.activity_detailed_person_screen)
    : BaseActivityWithoutViewModel<ActivityDetailedPersonScreenBinding>() {

    companion object{
        const val PERSON_INFO_FIELD = "PERSON_INFO_FIELD"

        fun startActivity(activity: Activity, personInfo: DetailedPersonInfo, viewTransition: View){
            val intent = Intent(activity, DetailedPersonScreenActivity::class.java)
            intent.putExtra(PERSON_INFO_FIELD, personInfo)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, viewTransition, "person_icon")
            activity.startActivity(intent, options.toBundle())
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent?.let {
            val personInfo = it.getParcelableExtra<DetailedPersonInfo>(PERSON_INFO_FIELD)
            personInfo?.let {
                binding.personInfo = personInfo
                binding.phoneButton.setOnClickListener {
                    val toDial = "tel:" + personInfo.personPhoneNumber
                    startActivity(Intent(Intent.ACTION_DIAL, Uri.parse(toDial)))
                }
            } ?: finish()
        }
    }
}