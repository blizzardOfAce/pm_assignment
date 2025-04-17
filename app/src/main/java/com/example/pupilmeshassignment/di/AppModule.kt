package com.example.pupilmeshassignment.di

import androidx.room.Room
import com.example.pupilmeshassignment.BuildConfig
import com.example.pupilmeshassignment.data.local.db.AppDatabase
import com.example.pupilmeshassignment.data.remote.MangaApi
import com.example.pupilmeshassignment.data.repository.MangaRepositoryImpl
import com.example.pupilmeshassignment.data.repository.UserRepositoryImpl
import com.example.pupilmeshassignment.domain.repository.MangaRepository
import com.example.pupilmeshassignment.domain.repository.UserRepository
import com.example.pupilmeshassignment.domain.useCase.manga.GetMangaListUseCase
import com.example.pupilmeshassignment.domain.useCase.signin.IsUserLoggedInUseCase
import com.example.pupilmeshassignment.domain.useCase.signin.SignInUseCase
import com.example.pupilmeshassignment.domain.useCase.signin.SignOutUseCase
import com.example.pupilmeshassignment.presentation.screens.facerecognition.FaceDetectionViewModel
import com.example.pupilmeshassignment.presentation.screens.manga.MangaDetailsViewModel
import com.example.pupilmeshassignment.presentation.screens.manga.MangaViewModel
import com.example.pupilmeshassignment.presentation.screens.signIn.LauncherViewModel
import com.example.pupilmeshassignment.presentation.screens.signIn.SignInViewModel
import com.example.pupilmeshassignment.utils.NetworkChecker
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val appModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(), AppDatabase::class.java, "my_app_db"
        ).fallbackToDestructiveMigration(false).build()
    }

    single { get<AppDatabase>().userDao() }
    single { get<AppDatabase>().mangaDao() }

    single<UserRepository> { UserRepositoryImpl(get(), androidApplication()) }
    single<MangaRepository> { MangaRepositoryImpl(get(), get(), get()) }

    //Use Cases
    factory { SignInUseCase(get()) }
    factory { SignOutUseCase(get()) }
    factory { IsUserLoggedInUseCase(get()) }
    factory { GetMangaListUseCase(get()) }

    // ViewModels
    viewModel { SignInViewModel(get(), get()) }
    viewModel { MangaViewModel(get(), get()) }
    viewModel { MangaDetailsViewModel(get()) }
    viewModel { FaceDetectionViewModel() }
    viewModel { LauncherViewModel(get()) }


    //Retrofit
    single<MangaApi> {
        Retrofit.Builder().baseUrl("https://mangaverse-api.p.rapidapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("X-RapidAPI-Key", BuildConfig.MANGAVERSE_API_KEY)
                    .addHeader("X-RapidAPI-Host", "mangaverse-api.p.rapidapi.com").build()
                chain.proceed(request)
            }.build()).build().create(MangaApi::class.java)
    }

    //NetworkChecker
    single { NetworkChecker(androidContext()) }


}
