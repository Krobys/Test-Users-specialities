package com.example.phonesapp.di.components

import com.example.phonesapp.PhoneListApp
import com.example.phonesapp.di.modules.AppModule
import com.example.phonesapp.di.modules.NetworkModule
import com.example.phonesapp.di.modules.ViewModelModule
import com.example.phonesapp.di.modules.builders.ActivityBuildersModule
import com.example.phonesapp.di.modules.builders.FragmentsBuilderModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class,
    NetworkModule::class,
    AppModule::class,
    ActivityBuildersModule::class,
    FragmentsBuilderModule::class,
    ViewModelModule::class])
interface AppComponent : AndroidInjector<PhoneListApp>{

    @Component.Builder
    abstract class Builder: AndroidInjector.Builder<PhoneListApp>()

}