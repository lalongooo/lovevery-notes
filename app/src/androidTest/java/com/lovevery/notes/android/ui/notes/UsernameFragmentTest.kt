package com.lovevery.notes.android.ui.notes

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.lovevery.notes.android.R
import com.lovevery.notes.android.ui.users.UsernameFragment
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UsernameFragmentTest {

    @Test
    fun testFragmentOnCreate() {
        launchFragmentInContainer<UsernameFragment>(themeResId = R.style.Theme_Loveverynotes)

        onView(withId(R.id.textViewLabel)).check(
            matches(withText("Enter your username"))
        )

        onView(withId(R.id.editTextUsername)).perform(
            typeText("@ironman"),
            closeSoftKeyboard()
        )
    }
}
