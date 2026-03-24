package app.peter.mos.data.di

import android.content.Context
import androidx.room.Room
import app.peter.mos.data.repositories.GoogleRepositoryImpl
import app.peter.mos.data.repositories.SeoulRepositoryImpl
import app.peter.mos.data.source.local.dao.CulturalEventDao
import app.peter.mos.data.source.remote.GoogleApi
import app.peter.mos.data.tool.db.AppDatabase
import app.peter.mos.data.tool.network.GoogleAuthInterceptor
import app.peter.mos.data.tool.network.Network
import app.peter.mos.domain.repository.GoogleRepository
import app.peter.mos.domain.repository.SeoulRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return Network.getClient()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
        return OkHttpClient.Builder().addInterceptor(logging).build()
    }

    @Provides
    fun provideRetrofitBuilder(okHttpClient: OkHttpClient): Retrofit.Builder {
        return Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
    }

    @Provides
    @Singleton
    fun provideGoogleApi(
            retrofitBuilder: Retrofit.Builder,
            okHttpClient: OkHttpClient,
            googleAuthInterceptor: GoogleAuthInterceptor
    ): GoogleApi {
        val client = okHttpClient.newBuilder().addInterceptor(googleAuthInterceptor).build()

        return retrofitBuilder
                .client(client)
                .baseUrl("https://www.googleapis.com/")
                .build()
                .create(GoogleApi::class.java)
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryBindingModule {

    @Binds
    @Singleton
    abstract fun bindSeoulRepository(seoulRepositoryImpl: SeoulRepositoryImpl): SeoulRepository

    @Binds
    @Singleton
    abstract fun bindGoogleRepository(googleRepositoryImpl: GoogleRepositoryImpl): GoogleRepository
}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "mos_database")
                .fallbackToDestructiveMigration()
                .build()
    }

    @Provides
    @Singleton
    fun provideCulturalEventDao(database: AppDatabase): CulturalEventDao {
        return database.culturalEventDao()
    }
}
