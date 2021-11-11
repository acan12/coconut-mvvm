package app.beelabs.coconut.mvvm.support.util

import android.annotation.SuppressLint
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import kotlin.random.Random


object EncryptionUtil {
    fun encryptedKey64(): ByteArray? {
        // security key to encrypted the Realm
        val key = ByteArray(64)
        Random(42).nextBytes(key)
        return key
    }

    @SuppressLint("NewApi")
    fun sha256(base: String): String? {
        return try {
            val digest: MessageDigest = MessageDigest.getInstance("SHA-256")
            val hash: ByteArray = digest.digest(base.toByteArray(StandardCharsets.UTF_8))
            val hexString = StringBuffer()
            for (i in hash.indices) {
                val hex = Integer.toHexString(0xff and hash[i].toInt())
                if (hex.length == 1) hexString.append('0')
                hexString.append(hex)
            }
            hexString.toString()
        } catch (ex: Exception) {
            throw RuntimeException(ex)
        }
    }
}