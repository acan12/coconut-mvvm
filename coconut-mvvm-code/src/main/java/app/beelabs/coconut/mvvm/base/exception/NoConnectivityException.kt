package app.beelabs.coconut.mvvm.base.exception

import java.io.IOException

class NoConnectivityException : IOException() {
    override val message: String?
        get() = "No network available, please check your WiFi or Data connection"
}