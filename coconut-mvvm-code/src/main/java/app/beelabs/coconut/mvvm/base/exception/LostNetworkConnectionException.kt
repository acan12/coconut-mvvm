package app.beelabs.coconut.mvvm.base.exception

import java.io.IOException

class LostNetworkConnectionException : IOException() {
    override val message: String?
        get() = "No network available, please check your WiFi or Data connection"
}