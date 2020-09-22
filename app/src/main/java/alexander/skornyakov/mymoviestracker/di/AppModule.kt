package alexander.skornyakov.mymoviestracker.di

import alexander.skornyakov.mymoviestracker.repository.FbRepository
import alexander.skornyakov.mymoviestracker.repository.TmdbRepository
import alexander.skornyakov.mymoviestracker.repository.api.TmdbApi
import alexander.skornyakov.mymoviestracker.repository.api.TmdbApiFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTmdbApi(): TmdbApi {
        return TmdbApiFactory.tmdbApi
    }

    @Provides
    @Singleton
    fun provideTmdbRepository(api: TmdbApi): TmdbRepository {
        return TmdbRepository(api)
    }

    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore {
        return Firebase.firestore
    }

    @Provides
    @Singleton
    fun provideAuth(): FirebaseAuth {
        return Firebase.auth
    }

    @Provides
    @Singleton
    fun provideFbRepository(
        firebaseAuth: FirebaseAuth,
        firestore: FirebaseFirestore,
        tmdbRepository: TmdbRepository
    ): FbRepository {
        return FbRepository(firestore, firebaseAuth, tmdbRepository)
    }

}