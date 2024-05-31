package com.arkan.terbangin.di

import android.content.SharedPreferences
import com.arkan.terbangin.data.datasource.auth.login.LoginApiDataSource
import com.arkan.terbangin.data.datasource.auth.login.LoginDataSource
import com.arkan.terbangin.data.datasource.auth.register.RegisterApiDataSource
import com.arkan.terbangin.data.datasource.auth.register.RegisterDataSource
import com.arkan.terbangin.data.datasource.auth.resetpassword.ResetPasswordApiDataSource
import com.arkan.terbangin.data.datasource.auth.resetpassword.ResetPasswordDataSource
import com.arkan.terbangin.data.datasource.user.UserDataSource
import com.arkan.terbangin.data.datasource.user.UserPreferenceDataSource
import com.arkan.terbangin.data.repository.UserRepository
import com.arkan.terbangin.data.repository.UserRepositoryImpl
import com.arkan.terbangin.data.repository.auth.LoginRepository
import com.arkan.terbangin.data.repository.auth.LoginRepositoryImpl
import com.arkan.terbangin.data.repository.auth.RegisterRepository
import com.arkan.terbangin.data.repository.auth.RegisterRepositoryImpl
import com.arkan.terbangin.data.repository.auth.ResetPasswordRepository
import com.arkan.terbangin.data.repository.auth.ResetPasswordRepositoryImpl
import com.arkan.terbangin.data.source.network.services.TerbanginApiServices
import com.arkan.terbangin.data.source.pref.UserPreference
import com.arkan.terbangin.data.source.pref.UserPreferenceImpl
import com.arkan.terbangin.presentation.auth.login.LoginViewModel
import com.arkan.terbangin.presentation.auth.register.RegisterViewModel
import com.arkan.terbangin.presentation.auth.reset_password.ResetPasswordViewModel
import com.arkan.terbangin.presentation.main.MainViewModel
import com.arkan.terbangin.presentation.passengers_count.PassengersCountViewModel
import com.arkan.terbangin.presentation.splash_screen.SplashViewModel
import com.arkan.terbangin.utils.SharedPreferenceUtils
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

object AppModules {
    private val apiModule =
        module {
            single<TerbanginApiServices> { TerbanginApiServices.invoke() }
        }

    private val localModule =
        module {
            single<SharedPreferences> {
                SharedPreferenceUtils.createPreference(
                    androidContext(),
                    UserPreferenceImpl.PREF_NAME,
                )
            }
            single<UserPreference> { UserPreferenceImpl(get()) }
        }

    private val dataSource =
        module {
            single<UserDataSource> { UserPreferenceDataSource(get()) }
            single<RegisterDataSource> { RegisterApiDataSource(get()) }
            single<LoginDataSource> { LoginApiDataSource(get()) }
            single<ResetPasswordDataSource> { ResetPasswordApiDataSource(get()) }
        }

    private val repository =
        module {
            single<UserRepository> { UserRepositoryImpl(get()) }
            single<RegisterRepository> { RegisterRepositoryImpl(get()) }
            single<LoginRepository> { LoginRepositoryImpl(get()) }
            single<ResetPasswordRepository> { ResetPasswordRepositoryImpl(get()) }
        }

    private val viewModelModule =
        module {
            viewModelOf(::SplashViewModel)
            viewModelOf(::MainViewModel)
            viewModelOf(::PassengersCountViewModel)
            viewModelOf(::RegisterViewModel)
            viewModelOf(::LoginViewModel)
            viewModelOf(::ResetPasswordViewModel)
        }

    val modules =
        listOf(
            apiModule,
            localModule,
            dataSource,
            repository,
            viewModelModule,
        )
}
