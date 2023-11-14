package com.example.practice_tvshowapp.rules

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

/**
 * 테스트 시작 전 Main Dispatcher 를 TestDispatcher 로 변경 -> 끝난 후에 reset
 */
class TestCoroutinesRule(
    private val testScope: TestScope = TestScope(),
    private val testDispatcher: TestDispatcher = StandardTestDispatcher(testScope.testScheduler)
): TestWatcher() {
    override fun starting(description: Description) {
        super.starting(description)

        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description) {
        super.finished(description)

        Dispatchers.resetMain()
    }

    fun runTest(block: suspend TestScope.() -> Unit) = testScope.runTest(testBody = block)

}
