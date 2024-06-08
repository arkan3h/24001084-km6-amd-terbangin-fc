package com.arkan.terbangin.di

import android.content.SharedPreferences
import com.arkan.terbangin.data.datasource.auth.login.LoginApiDataSource
import com.arkan.terbangin.data.datasource.auth.login.LoginDataSource
import com.arkan.terbangin.data.datasource.auth.otp.OTPApiDataSource
import com.arkan.terbangin.data.datasource.auth.otp.OTPDataSource
import com.arkan.terbangin.data.datasource.auth.register.RegisterApiDataSource
import com.arkan.terbangin.data.datasource.auth.register.RegisterDataSource
import com.arkan.terbangin.data.datasource.auth.resetpassword.ResetPasswordApiDataSource
import com.arkan.terbangin.data.datasource.auth.resetpassword.ResetPasswordDataSource
import com.arkan.terbangin.data.datasource.preference.PreferenceDataSource
import com.arkan.terbangin.data.datasource.preference.UserPreferenceDataSource
import com.arkan.terbangin.data.datasource.profile.ProfileApiDataSource
import com.arkan.terbangin.data.datasource.profile.ProfileDataSource
import com.arkan.terbangin.data.repository.UserPreferenceRepository
import com.arkan.terbangin.data.repository.UserPreferenceRepositoryImpl
import com.arkan.terbangin.data.repository.auth.LoginRepository
import com.arkan.terbangin.data.repository.auth.LoginRepositoryImpl
import com.arkan.terbangin.data.repository.auth.OTPRepository
import com.arkan.terbangin.data.repository.auth.OTPRepositoryImpl
import com.arkan.terbangin.data.repository.auth.RegisterRepository
import com.arkan.terbangin.data.repository.auth.RegisterRepositoryImpl
import com.arkan.terbangin.data.repository.auth.ResetPasswordRepository
import com.arkan.terbangin.data.repository.auth.ResetPasswordRepositoryImpl
import com.arkan.terbangin.data.repository.profile.ProfileRepository
import com.arkan.terbangin.data.repository.profile.ProfileRepositoryImpl
import com.arkan.terbangin.data.source.network.services.TerbanginApiServices
import com.arkan.terbangin.data.source.pref.UserPreference
import com.arkan.terbangin.data.source.pref.UserPreferenceImpl
import com.arkan.terbangin.presentation.auth.login.LoginViewModel
import com.arkan.terbangin.presentation.auth.otp.OTPViewModel
import com.arkan.terbangin.presentation.auth.register.RegisterViewModel
import com.arkan.terbangin.presentation.auth.reset_password.ResetPasswordViewModel
import com.arkan.terbangin.presentation.history.HistoryViewModel
import com.arkan.terbangin.presentation.home.HomeViewModel
import com.arkan.terbangin.presentation.home.passengers_count.PassengersCountViewModel
import com.arkan.terbangin.presentation.main.MainViewModel
import com.arkan.terbangin.presentation.notification.NotificationViewModel
import com.arkan.terbangin.presentation.profile.ProfileViewModel
import com.arkan.terbangin.presentation.profile.edit_profile.EditProfileViewModel
import com.arkan.terbangin.presentation.profile.setting_account.SettingAccountViewModel
import com.arkan.terbangin.presentation.splash_screen.SplashViewModel
import com.arkan.terbangin.utils.SharedPreferenceUtils
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

object AppModules {
    private val apiModule =
        module {
            single<TerbanginApiServices> { TerbanginApiServices.invoke(get()) }
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
            single<PreferenceDataSource> { UserPreferenceDataSource(get()) }
            single<RegisterDataSource> { RegisterApiDataSource(get()) }
            single<LoginDataSource> { LoginApiDataSource(get()) }
            single<ResetPasswordDataSource> { ResetPasswordApiDataSource(get()) }
            single<OTPDataSource> { OTPApiDataSource(get()) }
            single<ProfileDataSource> { ProfileApiDataSource(get()) }
        }

    private val repository =
        module {
            single<UserPreferenceRepository> { UserPreferenceRepositoryImpl(get()) }
            single<RegisterRepository> { RegisterRepositoryImpl(get()) }
            single<LoginRepository> { LoginRepositoryImpl(get(), get()) }
            single<ResetPasswordRepository> { ResetPasswordRepositoryImpl(get()) }
            single<OTPRepository> { OTPRepositoryImpl(get()) }
            single<ProfileRepository> { ProfileRepositoryImpl(get()) }
        }

    private val viewModelModule =
        module {
            viewModelOf(::SplashViewModel)
            viewModelOf(::MainViewModel)
            viewModelOf(::PassengersCountViewModel)
            viewModelOf(::RegisterViewModel)
            viewModelOf(::LoginViewModel)
            viewModelOf(::ResetPasswordViewModel)
            viewModelOf(::OTPViewModel)
            viewModelOf(::HomeViewModel)
            viewModelOf(::HistoryViewModel)
            viewModelOf(::NotificationViewModel)
            viewModelOf(::EditProfileViewModel)
            viewModelOf(::SettingAccountViewModel)
            viewModelOf(::ProfileViewModel)
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
