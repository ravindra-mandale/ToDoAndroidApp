package com.programminginmyway.todoappnew.Utils

import java.security.MessageDigest

object PasswordUtils {

    fun hashPassword(password: String): String {
        val bytes = password.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return digest.joinToString("") { "%02x".format(it) }
    }

    fun isPasswordValid(inputPassword: String, storedHash: String): Boolean {
        return PasswordUtils.hashPassword(inputPassword) == storedHash
    }
}
