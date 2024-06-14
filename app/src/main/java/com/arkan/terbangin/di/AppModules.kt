package com.arkan.terbangin.di

import android.content.SharedPreferences
import com.arkan.terbangin.data.datasource.airportcity.AirportCityDataSource
import com.arkan.terbangin.data.datasource.airportcity.AirportCityDataSourceImpl
import com.arkan.terbangin.data.datasource.auth.login.LoginApiDataSource
import com.arkan.terbangin.data.datasource.auth.login.LoginDataSource
import com.arkan.terbangin.data.datasource.auth.otp.OTPApiDataSource
import com.arkan.terbangin.data.datasource.auth.otp.OTPDataSource
import com.arkan.terbangin.data.datasource.auth.register.RegisterApiDataSource
import com.arkan.terbangin.data.datasource.auth.register.RegisterDataSource
import com.arkan.terbangin.data.datasource.auth.resetpassword.ResetPasswordApiDataSource
import com.arkan.terbangin.data.datasource.auth.resetpassword.ResetPasswordDataSource
import com.arkan.terbangin.data.datasource.flight.FlightApiDataSource
import com.arkan.terbangin.data.datasource.flight.FlightDataSource
import com.arkan.terbangin.data.datasource.passenger.PassengerApiDataSource
import com.arkan.terbangin.data.datasource.passenger.PassengerDataSource
import com.arkan.terbangin.data.datasource.preference.PreferenceDataSource
import com.arkan.terbangin.data.datasource.preference.UserPreferenceDataSource
import com.arkan.terbangin.data.datasource.profile.ProfileApiDataSource
import com.arkan.terbangin.data.datasource.profile.ProfileDataSource
import com.arkan.terbangin.data.repository.airport.AirportCityRepository
import com.arkan.terbangin.data.repository.airport.AirportCityRepositoryImpl
import com.arkan.terbangin.data.repository.auth.LoginRepository
import com.arkan.terbangin.data.repository.auth.LoginRepositoryImpl
import com.arkan.terbangin.data.repository.auth.OTPRepository
import com.arkan.terbangin.data.repository.auth.OTPRepositoryImpl
import com.arkan.terbangin.data.repository.auth.RegisterRepository
import com.arkan.terbangin.data.repository.auth.RegisterRepositoryImpl
import com.arkan.terbangin.data.repository.auth.ResetPasswordRepository
import com.arkan.terbangin.data.repository.auth.ResetPasswordRepositoryImpl
import com.arkan.terbangin.data.repository.flight.FlightRepository
import com.arkan.terbangin.data.repository.flight.FlightRepositoryImpl
import com.arkan.terbangin.data.repository.passenger.PassengerRepository
import com.arkan.terbangin.data.repository.passenger.PassengerRepositoryImpl
import com.arkan.terbangin.data.repository.pref.UserPreferenceRepository
import com.arkan.terbangin.data.repository.pref.UserPreferenceRepositoryImpl
import com.arkan.terbangin.data.repository.profile.ProfileRepository
import com.arkan.terbangin.data.repository.profile.ProfileRepositoryImpl
import com.arkan.terbangin.data.source.network.services.TerbanginApiServices
import com.arkan.terbangin.data.source.pref.UserPreference
import com.arkan.terbangin.data.source.pref.UserPreferenceImpl
import com.arkan.terbangin.presentation.auth.login.LoginViewModel
import com.arkan.terbangin.presentation.auth.otp.OTPViewModel
import com.arkan.terbangin.presentation.auth.register.RegisterViewModel
import com.arkan.terbangin.presentation.auth.reset_password.ResetPasswordViewModel
import com.arkan.terbangin.presentation.checkout.orderbiodata.OrderBiodataViewModel
import com.arkan.terbangin.presentation.checkout.passengerbiodata.PassengerBioDataViewModel
import com.arkan.terbangin.presentation.flightdetail.FlightDetailViewModel
import com.arkan.terbangin.presentation.flightsearch.FlightSearchViewModel
import com.arkan.terbangin.presentation.flightsearch.filter_list.FilterListViewModel
import com.arkan.terbangin.presentation.history.HistoryViewModel
import com.arkan.terbangin.presentation.home.HomeViewModel
import com.arkan.terbangin.presentation.home.class_sheet.ClassSheetViewModel
import com.arkan.terbangin.presentation.home.passengers_count.PassengersCountViewModel
import com.arkan.terbangin.presentation.home.terminal_search.TerminalSearchViewModel
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
            single<FlightDataSource> { FlightApiDataSource(get()) }
            single<AirportCityDataSource> { AirportCityDataSourceImpl(get()) }
            single<PassengerDataSource> { PassengerApiDataSource(get()) }
        }

    private val repository =
        module {
            single<UserPreferenceRepository> { UserPreferenceRepositoryImpl(get()) }
            single<RegisterRepository> { RegisterRepositoryImpl(get()) }
            single<LoginRepository> { LoginRepositoryImpl(get(), get()) }
            single<ResetPasswordRepository> { ResetPasswordRepositoryImpl(get()) }
            single<OTPRepository> { OTPRepositoryImpl(get()) }
            single<ProfileRepository> { ProfileRepositoryImpl(get()) }
            single<FlightRepository> { FlightRepositoryImpl(get()) }
            single<AirportCityRepository> { AirportCityRepositoryImpl(get()) }
            single<PassengerRepository> { PassengerRepositoryImpl(get()) }
        }

    private val viewModelModule =
        module {
            viewModelOf(::SplashViewModel)
            viewModelOf(::MainViewModel)
            viewModelOf(::PassengersCountViewModel)
            viewModelOf(::ClassSheetViewModel)
            viewModelOf(::FilterListViewModel)
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
            viewModelOf(::FlightSearchViewModel)
            viewModelOf(::TerminalSearchViewModel)
            viewModelOf(::FlightDetailViewModel)
            viewModelOf(::OrderBiodataViewModel)
            viewModelOf(::PassengerBioDataViewModel)
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
