package app.peter.mos.data.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    @Named("seoul_key")
    fun provideSeoulKey(@ApplicationContext context: Context): String {
        return context.getString(
            context.resources.getIdentifier("seoul_key", "string", context.packageName)
        )
    }
}
