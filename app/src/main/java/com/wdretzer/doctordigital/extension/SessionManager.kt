package com.wdretzer.doctordigital.extension

import android.util.LruCache
import com.wdretzer.doctordigital.model.LoginUser

object SessionManager {
    private const val SESSION_TOKEN_KEY = "session_token_key"
    private const val SESSION_PROFILE_KEY = "session_profile_key"

    private val cache = LruCache<String, Any>(1024)
    val session: String?
        get() = cache.get(SESSION_TOKEN_KEY) as? String

    val profile: LoginUser?
        get() = cache.get(SESSION_PROFILE_KEY) as? LoginUser

    fun saveSession(session: String) {
        cache.put(SESSION_TOKEN_KEY, session)
    }

    fun saveProfile(profile: LoginUser) {
        cache.put(SESSION_PROFILE_KEY, profile)
    }
}
