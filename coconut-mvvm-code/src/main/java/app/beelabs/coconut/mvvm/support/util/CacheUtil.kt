package app.beelabs.coconut.mvvm.support.util

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import app.beelabs.coconut.mvvm.R
import java.io.IOException
import java.security.GeneralSecurityException


object CacheUtil {
    val PREFERENCE_KEY = R.string.app_name
    lateinit var sharedPref: SharedPreferences

    fun setupSharedPref(
        applicationName: String,
        context: Context
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                sharedPref = EncryptedSharedPreferences.create(
                    applicationName,
                    MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
                    context,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
                )
            } catch (e: GeneralSecurityException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        } else {
            sharedPref =
                context.getSharedPreferences(
                    context.getString(PREFERENCE_KEY),
                    Context.MODE_PRIVATE
                );
        }

    }

    fun putPreferenceString(key: String, value: String, context: Context) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString(key, value).commit()
    }

    fun putPreferenceInteger(key: String?, value: Int, context: Context?) {
        val editor = sharedPref.edit()
        editor.putInt(key, value).commit()
    }

    fun putPreferenceBoolean(key: String?, value: Boolean, context: Context?) {
        val editor = sharedPref.edit()
        editor.putBoolean(key, value).commit()
    }

    fun getPreferenceString(key: String?, context: Context?): String? {
//        sharedPref = context.getSharedPreferences(context.getString(PREFERENCE_KEY), Context.MODE_PRIVATE);
        return sharedPref.getString(key, "")
    }

    fun getPreferenceInteger(key: String?, context: Context?): Int {
//        sharedPref = context.getSharedPreferences(context.getString(PREFERENCE_KEY), Context.MODE_PRIVATE);
        return sharedPref.getInt(key, 0)
    }

    fun getPreferenceBoolean(key: String?, context: Context?): Boolean? {
//        sharedPref = context.getSharedPreferences(context.getString(PREFERENCE_KEY), Context.MODE_PRIVATE);
        return sharedPref.getBoolean(key, false)
    }

    fun clearPreference(context: Context) {
        sharedPref =
            context.getSharedPreferences(context.getString(PREFERENCE_KEY), Context.MODE_PRIVATE)
        sharedPref.edit().clear().commit()
    }
}