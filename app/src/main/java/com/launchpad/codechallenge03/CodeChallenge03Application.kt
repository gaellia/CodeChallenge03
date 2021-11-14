package com.launchpad.codechallenge03

import android.app.Application
import com.launchpad.codechallenge03.di.mainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class CodeChallenge03Application: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@CodeChallenge03Application)
            modules(mainModule)
        }
    }
}