package app.beelabs.coconut.mvvm.di.component

import app.beelabs.coconut.mvvm.base.interfaces.IApi
import app.beelabs.coconut.mvvm.base.interfaces.IProgress

interface AppComponent {

    fun getApi(): IApi
    fun getProgressDialog(): IProgress
}