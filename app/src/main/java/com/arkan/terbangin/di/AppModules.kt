package com.arkan.terbangin.di

import android.content.SharedPreferences
import com.arkan.terbangin.base.BaseViewModel
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
import com.arkan.terbangin.data.datasource.booking2.BookingApiDataSource2
import com.arkan.terbangin.data.datasource.booking2.BookingDataSource2
import com.arkan.terbangin.data.datasource.booking2.HelperBookingApiDataSource2
import com.arkan.terbangin.data.datasource.booking2.HelperBookingDataSource2
import com.arkan.terbangin.data.datasource.flight.FlightApiDataSource
import com.arkan.terbangin.data.datasource.flight.FlightDataSource
import com.arkan.terbangin.data.datasource.history.HistoryDataSource
import com.arkan.terbangin.data.datasource.history.HistoryDataSourceImpl
import com.arkan.terbangin.data.datasource.notification.NotificationApiDataSource
import com.arkan.terbangin.data.datasource.notification.NotificationDataSource
import com.arkan.terbangin.data.datasource.passenger.PassengerApiDataSource
import com.arkan.terbangin.data.datasource.passenger.PassengerDataSource
import com.arkan.terbangin.data.datasource.payment.PaymentApiDataSource
import com.arkan.terbangin.data.datasource.payment.PaymentDataSource
import com.arkan.terbangin.data.datasource.preference.PreferenceDataSource
import com.arkan.terbangin.data.datasource.preference.UserPreferenceDataSource
import com.arkan.terbangin.data.datasource.profile.ProfileApiDataSource
import com.arkan.terbangin.data.datasource.profile.ProfileDataSource
import com.arkan.terbangin.data.datasource.searchHistory.SearchHistoryDataSource
import com.arkan.terbangin.data.datasource.searchHistory.SearchHistoryDatabaseDataSource
import com.arkan.terbangin.data.datasource.searchHistory.SearchTerminalDataSource
import com.arkan.terbangin.data.datasource.searchHistory.SearchTerminalDatabaseDataSource
import com.arkan.terbangin.data.datasource.seat.SeatApiDataSource
import com.arkan.terbangin.data.datasource.seat.SeatDataSource
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
import com.arkan.terbangin.data.repository.history.HistoryRepository
import com.arkan.terbangin.data.repository.history.HistoryRepositoryImpl
import com.arkan.terbangin.data.repository.notification.NotificationRepository
import com.arkan.terbangin.data.repository.notification.NotificationRepositoryImpl
import com.arkan.terbangin.data.repository.passenger.PassengerRepository
import com.arkan.terbangin.data.repository.passenger.PassengerRepositoryImpl
import com.arkan.terbangin.data.repository.payment.PaymentRepository
import com.arkan.terbangin.data.repository.payment.PaymentRepositoryImpl
import com.arkan.terbangin.data.repository.pref.UserPreferenceRepository
import com.arkan.terbangin.data.repository.pref.UserPreferenceRepositoryImpl
import com.arkan.terbangin.data.repository.profile.ProfileRepository
import com.arkan.terbangin.data.repository.profile.ProfileRepositoryImpl
import com.arkan.terbangin.data.repository.searchhistory.SearchHistoryRepository
import com.arkan.terbangin.data.repository.searchhistory.SearchHistoryRepositoryImpl
import com.arkan.terbangin.data.repository.searchhistory.SearchTerminalRepository
import com.arkan.terbangin.data.repository.searchhistory.SearchTerminalRepositoryImpl
import com.arkan.terbangin.data.repository.seat.SeatRepository
import com.arkan.terbangin.data.repository.seat.SeatRepositoryImpl
import com.arkan.terbangin.data.source.local.AppDatabase
import com.arkan.terbangin.data.source.local.dao.SearchHistoryDao
import com.arkan.terbangin.data.source.local.dao.SearchTerminalDao
import com.arkan.terbangin.data.source.network.services.TerbanginApiServices
import com.arkan.terbangin.data.source.pref.UserPreference
import com.arkan.terbangin.data.source.pref.UserPreferenceImpl
import com.arkan.terbangin.presentation.auth.login.LoginViewModel
import com.arkan.terbangin.presentation.auth.otp.OTPViewModel
import com.arkan.terbangin.presentation.auth.register.RegisterViewModel
import com.arkan.terbangin.presentation.auth.reset_password.ResetPasswordViewModel
import com.arkan.terbangin.presentation.checkout.detail.CheckoutDetailViewModel
import com.arkan.terbangin.presentation.checkout.orderbiodata.OrderBiodataViewModel
import com.arkan.terbangin.presentation.checkout.passengerbiodata.PassengerBioDataViewModel
import com.arkan.terbangin.presentation.checkout.payment.PaymentViewModel
import com.arkan.terbangin.presentation.checkout.selectpassengerseat.SelectPassengerSeatViewModel
import com.arkan.terbangin.presentation.checkout.selectpassengerseat.returnflight.SelectReturnPassengerSeatViewModel
import com.arkan.terbangin.presentation.flightdetail.FlightDetailViewModel
import com.arkan.terbangin.presentation.flightsearch.FlightSearchViewModel
import com.arkan.terbangin.presentation.flightsearch.filter_list.FilterListViewModel
import com.arkan.terbangin.presentation.flightsearch.flightreturnsearch.FlightSearchReturnViewModel
import com.arkan.terbangin.presentation.history.HistoryViewModel
import com.arkan.terbangin.presentation.history.filterhistory.FilterHistorySheetViewModel
import com.arkan.terbangin.presentation.history.searchhistory.HistorySearchViewModel
import com.arkan.terbangin.presentation.home.HomeViewModel
import com.arkan.terbangin.presentation.home.class_sheet.ClassSheetViewModel
import com.arkan.terbangin.presentation.home.passengers_count.PassengersCountViewModel
import com.arkan.terbangin.presentation.home.terminal_search.TerminalSearchViewModel
import com.arkan.terbangin.presentation.main.MainViewModel
import com.arkan.terbangin.presentation.notification.NotificationViewModel
import com.arkan.terbangin.presentation.profile.ProfileViewModel
import com.arkan.terbangin.presentation.profile.edit_profile.EditProfileViewModel
import com.arkan.terbangin.presentation.profile.setting_account.SettingAccountViewModel
import com.arkan.terbangin.presentation.splashscreen.SplashViewModel
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
            single<AppDatabase> { AppDatabase.getInstance(androidContext()) }
            single<SearchHistoryDao> { get<AppDatabase>().searchHistoryDao() }
            single<SearchTerminalDao> { get<AppDatabase>().searchTerminalDao() }
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
            single<SearchHistoryDataSource> { SearchHistoryDatabaseDataSource(get()) }
            single<SearchTerminalDataSource> { SearchTerminalDatabaseDataSource(get()) }
            single<NotificationDataSource> { NotificationApiDataSource(get()) }
            single<SeatDataSource> { SeatApiDataSource(get()) }
            single<PaymentDataSource> { PaymentApiDataSource(get()) }
            single<BookingDataSource2> { BookingApiDataSource2(get()) }
            single<HelperBookingDataSource2> { HelperBookingApiDataSource2(get()) }
            single<HistoryDataSource> { HistoryDataSourceImpl(get()) }
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
            single<SearchHistoryRepository> { SearchHistoryRepositoryImpl(get()) }
            single<SearchTerminalRepository> { SearchTerminalRepositoryImpl(get()) }
            single<NotificationRepository> { NotificationRepositoryImpl(get()) }
            single<SeatRepository> { SeatRepositoryImpl(get()) }
            single<PaymentRepository> { PaymentRepositoryImpl(get(), get(), get(), get()) }
            single<HistoryRepository> { HistoryRepositoryImpl(get()) }
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
            viewModelOf(::FlightSearchReturnViewModel)
            viewModelOf(::TerminalSearchViewModel)
            viewModelOf(::FlightDetailViewModel)
            viewModelOf(::OrderBiodataViewModel)
            viewModelOf(::PassengerBioDataViewModel)
            viewModelOf(::SelectPassengerSeatViewModel)
            viewModelOf(::SelectReturnPassengerSeatViewModel)
            viewModelOf(::HistorySearchViewModel)
            viewModelOf(::BaseViewModel)
            viewModelOf(::CheckoutDetailViewModel)
            viewModelOf(::PaymentViewModel)
            viewModelOf(::FilterHistorySheetViewModel)
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
