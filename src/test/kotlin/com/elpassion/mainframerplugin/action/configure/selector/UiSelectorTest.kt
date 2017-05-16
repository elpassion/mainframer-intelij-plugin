package com.elpassion.mainframerplugin.action.configure.selector

import com.elpassion.android.commons.rxjavatest.thenJust
import com.intellij.execution.configurations.RunConfiguration
import com.intellij.testFramework.fixtures.LightPlatformCodeInsightFixtureTestCase
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever

class UiSelectorTest : LightPlatformCodeInsightFixtureTestCase() {

    private val uiSelector = mock<UiSelector>()

    fun testShouldReturnEmptyItemsListWhenNoConfigurationInProject() {
        val result = SelectorResult(emptyList(), emptyList())
        whenever(uiSelector.invoke(any(), any())).thenJust(result)
        selector(project, uiSelector).invoke().test().assertValue { it == result }
    }

    fun testShouldReturnSelectedConfigurationOnChangeInUi() {
        val configuration = mock<RunConfiguration>()
        val result = SelectorResult(listOf(configuration), emptyList())
        whenever(uiSelector.invoke(any(), any())).thenJust(result)
        selector(project, uiSelector).invoke().test().assertValue { it == result }
    }
}