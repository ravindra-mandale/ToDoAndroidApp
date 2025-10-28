package com.programminginmyway.todoappnew

import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.programminginmyway.todoappnew.screens.RegistrationScreen
import org.hamcrest.CoreMatchers.not
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test for the RegistrationScreen.
 * This test will run on an Android device or emulator.
 */
@RunWith(AndroidJUnit4::class)
class RegistrationScreenTest {

    /**
     * Launches the RegistrationScreen before each test.
     * ActivityScenario is the modern way to launch an activity for testing.
     * It ensures tests are isolated and handles lifecycle events correctly.
     */
    @Before
    fun setup() {
        ActivityScenario.launch(RegistrationScreen::class.java)
    }

    @Test
    fun registerButton_whenAllFieldsAreEmpty_showsAllErrors() {
        // 1. Action: Click the register button without entering any text
        onView(withId(R.id.button_register)).perform(click())

        // 2. Verification: Check that error messages are visible for each field.
        // We find the TextViews by their text content since they don't have unique IDs.
        // This is a good example of how your XML's error TextViews are being tested.
        onView(withText(R.string.enter_valid_email_address)).check(matches(isDisplayed()))
        onView(ViewMatchers.withText(R.string.fullname_not_entered_error_message)).check(matches(isDisplayed()))
        onView(ViewMatchers.withText(R.string.enter_valid_mobile_number)).check(matches(isDisplayed()))
        onView(ViewMatchers.withText(R.string.password_not_entered_error_message)).check(matches(isDisplayed()))
    }

    @Test
    fun emailField_whenInvalidEmailEntered_showsEmailError() {
        // 1. Action: Type an invalid email into the EditText
        onView(withId(R.id.edittext_email_id)).perform(typeText("invalid-email"), closeSoftKeyboard())

        // 2. Action: Click the register button to trigger validation
        onView(withId(R.id.button_register)).perform(click())

        // 3. Verification: Check that the email error TextView is now visible
        onView(withText(R.string.enter_valid_email_address)).check(matches(isDisplayed()))
    }

    @Test
    fun passwordFields_whenPasswordsDoNotMatch_showsConfirmPasswordError() {
        // 1. Action: Type a password
        onView(withId(R.id.edittext_password)).perform(typeText("Password123"), closeSoftKeyboard())

        // 2. Action: Type a different password in the confirm password field
        onView(withId(R.id.edittext_confirm_password)).perform(typeText("Password456"), closeSoftKeyboard())

        // 3. Action: Click the register button to trigger validation
        onView(withId(R.id.button_register)).perform(click())

        // 4. Verification: Check that the password mismatch error is displayed
        onView(ViewMatchers.withText(R.string.password_confirm_password_not_same_error_message)).check(matches(isDisplayed()))
    }

    @Test
    fun allFields_whenValidDataEntered_noErrorsAreShown() {
        // This is the "happy path" test.

        // 1. Actions: Fill all fields with valid data
        onView(withId(R.id.edittext_email_id)).perform(typeText("test.user@example.com"), closeSoftKeyboard())
        onView(withId(R.id.edittext_full_name)).perform(typeText("Test User"), closeSoftKeyboard())
        onView(withId(R.id.edittext_mobile_number)).perform(typeText("1234567890"), closeSoftKeyboard())
        onView(withId(R.id.edittext_password)).perform(typeText("Password123"), closeSoftKeyboard())
        onView(withId(R.id.edittext_confirm_password)).perform(typeText("Password123"), closeSoftKeyboard())

        // 2. Action: Click the register button
        onView(withId(R.id.button_register)).perform(click())

        // 3. Verification: Assert that NONE of the error TextViews are visible.
        // This confirms that your ViewModel's validation logic is working correctly for valid inputs.
        onView(withText(R.string.enter_valid_email_address)).check(matches(not(isDisplayed())))
        onView(ViewMatchers.withText(R.string.fullname_not_entered_error_message)).check(matches(not(isDisplayed())))
        onView(ViewMatchers.withText(R.string.enter_valid_mobile_number)).check(matches(not(isDisplayed())))
        onView(ViewMatchers.withText(R.string.password_not_entered_error_message)).check(matches(not(isDisplayed())))
        onView(ViewMatchers.withText(R.string.password_confirm_password_not_same_error_message)).check(matches(not(isDisplayed())))
    }
}
