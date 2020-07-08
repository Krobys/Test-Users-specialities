package com.example.phonesapp.di.modules.builders

import android.app.Application
import com.example.phonesapp.ui.mainscreen.MainActivity
import com.example.phonesapp.PhoneListApp
import com.example.phonesapp.ui.detailedscreen.DetailedPersonScreenActivity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {

    @Binds
    abstract fun bindPhoneListApp(app: PhoneListApp): Application

    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun bindDetailedPersonActivity(): DetailedPersonScreenActivity
}