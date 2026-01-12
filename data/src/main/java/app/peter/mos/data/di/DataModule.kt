package app.peter.mos.data.di

import app.peter.mos.data.repositories.SeoulRepository
import app.peter.mos.data.tool.network.Network
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return Network.getClient()
    }
}

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideSeoulRepository(
        @Named("seoul_key") seoulKey: String
    ): SeoulRepository {
        return SeoulRepository(seoulKey)
    }
}

