package com.moltaworks.githubsearchapp.ui

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performImeAction
import androidx.compose.ui.test.performTextInput
import com.moltaworks.githubsearchapp.Constant.SEARCHFIELD_TAG
import com.moltaworks.githubsearchapp.di.AppModule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.Timer
import kotlin.concurrent.schedule

@HiltAndroidTest
@UninstallModules(AppModule::class)
class PagingListScreenKtTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun `Test_if_the_Test_workes_at_all`() {
        composeTestRule.onNodeWithTag(SEARCHFIELD_TAG).assertExists().performTextInput("test")
        composeTestRule.onNodeWithTag(SEARCHFIELD_TAG)
            .performImeAction()
        // AsyncTimer to have a look at the beautyfull screen for 10 sec
        asyncTimer(10000) // remove for real Test!!!
    }

    fun asyncTimer(delay: Long = 1000) {
        AsyncTimer.start(delay)
        composeTestRule.waitUntil(
            condition = { AsyncTimer.expired },
            timeoutMillis = delay + 1000
        )
    }
}

object AsyncTimer {
    var expired = false
    fun start(delay: Long = 1000) {
        expired = false
        Timer().schedule(delay) {
            expired = true
        }
    }
}
