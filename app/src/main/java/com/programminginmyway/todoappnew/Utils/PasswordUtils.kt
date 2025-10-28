package com.programminginmyway.todoappnew.Utils

import java.security.MessageDigest

object PasswordUtils {
    //singleton object- this PasswordUtils class has only one instance throughout the application, no need to use new keyword

    fun hashPassword(password: String): String {
        val bytes = password.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return digest.joinToString("") { "%02x".format(it) }
    }

    fun isPasswordValid(inputPassword: String, storedHash: String): Boolean {
        return hashPassword(inputPassword) == storedHash
    }
}
