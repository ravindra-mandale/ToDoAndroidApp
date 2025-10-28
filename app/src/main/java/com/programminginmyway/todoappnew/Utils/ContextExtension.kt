package com.programminginmyway.todoappnew.Utils

// in a new file, e.g., extensions/ContextExtensions.kt
import android.content.Context
import android.widget.Toast

/**
 * Shows a Toast message using a String resource ID.
 *
 * @param stringResId The resource ID of the string to display.
 * @param duration The duration for the Toast. Defaults to Toast.LENGTH_SHORT.
 */
fun Context.showToast(stringResId: Int, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this.applicationContext, stringResId, duration).show()
}

/**
 * Shows a Toast message using a direct String.
 *
 * @param message The string message to display.
 * @param duration The duration for the Toast. Defaults to Toast.LENGTH_SHORT.
 */
fun Context.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this.applicationContext, message, duration).show()
}
