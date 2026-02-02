package app.peter.mos.data.di

import app.peter.mos.data.repositories.SeoulRepositoryImpl
import app.peter.mos.data.tool.network.Network
import app.peter.mos.domain.repository.SeoulRepository
import dagger.Binds
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
    fun provideSeoulRepository(@Named("seoul_key") seoulKey: String): SeoulRepositoryImpl {
        return SeoulRepositoryImpl(seoulKey)
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryBindingModule {

    @Binds
    @Singleton
    abstract fun bindSeoulRepository(seoulRepositoryImpl: SeoulRepositoryImpl): SeoulRepository
}
