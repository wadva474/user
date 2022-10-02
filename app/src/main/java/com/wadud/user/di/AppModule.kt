package com.wadud.user.di

import android.content.Context
import androidx.room.Room
import com.wadud.user.BuildConfig
import com.wadud.user.data.local.UserDao
import com.wadud.user.data.local.UserDatabase
import com.wadud.user.data.remote.api.UserApiService
import dagger.Lazy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Singleton
    @Provides
    private fun httpLoggingInterceptor(): HttpLoggingInterceptor {
        val logLevel =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = logLevel
        return loggingInterceptor
    }

    fun provideGenericOkHttpClient(
        interceptor: HttpLoggingInterceptor
    ): OkHttpClient = OkHttpClient().newBuilder()
        .addInterceptor(interceptor).build()

    @Provides
    @Singleton
    fun provideUserApi(
        client: Lazy<OkHttpClient>
    ): UserApiService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client.get())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(UserApiService::class.java)

    }

    @Provides
    @Singleton
    fun providesUserDatabase(@ApplicationContext context: Context): UserDatabase {
        return Room.databaseBuilder(context, UserDatabase::class.java, "user.db").build()
    }

    @Provides
    @Singleton
    fun provideUserDao(database: UserDatabase): UserDao {
        return database.userDao
    }


}