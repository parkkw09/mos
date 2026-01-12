package app.peter.mos

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MosApplication : Application() {
    
    companion object {
        const val TAG = "MOS"
    }
    
    override fun onCreate() {
        super.onCreate()
    }
}
