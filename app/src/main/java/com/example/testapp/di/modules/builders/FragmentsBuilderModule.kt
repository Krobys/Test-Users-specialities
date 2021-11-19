package com.example.testapp.di.modules.builders

import com.example.testapp.ui.users.detailedUser.DetailedUserFragment
import com.example.testapp.ui.users.specialities.SpecialitiesFragment
import com.example.testapp.ui.users.usersList.UsersFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface FragmentsBuilderModule {

    @ContributesAndroidInjector
    fun contributeSpecialitiesFragment(): SpecialitiesFragment

    @ContributesAndroidInjector
    fun contributeUsersFragment(): UsersFragment

    @ContributesAndroidInjector
    fun contributeDetailedUserFragment(): DetailedUserFragment
}