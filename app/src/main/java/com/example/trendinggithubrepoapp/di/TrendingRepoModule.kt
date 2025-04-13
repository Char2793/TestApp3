package com.example.trendinggithubrepoapp.di

import com.example.trendinggithubrepoapp.data.TrendingRepoDataSource
import com.example.trendinggithubrepoapp.data.network.TrendingAPI
import com.example.trendinggithubrepoapp.data.repository.TrendingRepo
import com.example.trendinggithubrepoapp.domain.TrendingRepoDataSourceImpl
import com.example.trendinggithubrepoapp.domain.TrendingRepoImpl
import com.example.trendinggithubrepoapp.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TrendingRepoModule {

    @Provides
    @Singleton
    fun providesLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
       return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun providesTrendingRepoApi(
        okHttpClient: OkHttpClient,
    ): TrendingAPI {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TrendingAPI::class.java)
    }

    @Provides
    @Singleton
    fun providesDataSource(
        trendingAPI: TrendingAPI
    ): TrendingRepoDataSource = TrendingRepoDataSourceImpl(trendingAPI)

    @Provides
    @Singleton
    fun providesTrendingRepo(
        trendingRepoDataSource: TrendingRepoDataSource
    ): TrendingRepo = TrendingRepoImpl(trendingRepoDataSource)

}