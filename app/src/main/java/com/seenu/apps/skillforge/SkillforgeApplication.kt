package com.seenu.apps.skillforge

import android.app.Application
import com.seenu.apps.skillforge.di.AppContainer
import com.seenu.apps.skillforge.di.AppContainerImpl

class SkillforgeApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppContainerImpl()
    }
}
