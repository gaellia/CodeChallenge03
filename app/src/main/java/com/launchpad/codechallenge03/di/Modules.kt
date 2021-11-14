package com.launchpad.codechallenge03.di

import com.launchpad.codechallenge03.repo.DB
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val mainModule = module {
    single { DB.buildDatabase(androidContext()) }
}