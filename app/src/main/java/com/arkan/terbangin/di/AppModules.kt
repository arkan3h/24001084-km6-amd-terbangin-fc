package com.arkan.terbangin.di

import org.koin.dsl.module

object AppModules {
    private val apiModule = module { }

    private val dataSource = module { }

    private val repository = module { }

    private val viewModelModule = module { }

    val modules =
        listOf(
            apiModule,
            dataSource,
            repository,
            viewModelModule,
        )
}
