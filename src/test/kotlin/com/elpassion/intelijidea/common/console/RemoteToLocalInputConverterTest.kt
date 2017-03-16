package com.elpassion.intelijidea.common.console

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class RemoteToLocalInputConverterTest {

    private val PROJECT_NAME = "testProject"

    @Test
    fun `Should catch file path from remote machine`() {
        val converter = RemoteToLocalInputConverter(PROJECT_NAME)
        assertTrue(converter.FILE_PATH_REGEX.matches("/mainframer/$PROJECT_NAME"))
    }

    @Test
    fun `Should not catch file path if it is not from remote machine`() {
        val converter = RemoteToLocalInputConverter(PROJECT_NAME)
        assertFalse(converter.FILE_PATH_REGEX.matches("/$PROJECT_NAME"))
    }

    @Test
    fun `Should catch file path if it starts with longer path`() {
        val converter = RemoteToLocalInputConverter(PROJECT_NAME)
        assertTrue(converter.FILE_PATH_REGEX.matches("/longer/path/mainframer/$PROJECT_NAME"))
    }

    @Test
    fun `Should catch file path if it ends with kotlin class name`() {
        val converter = RemoteToLocalInputConverter(PROJECT_NAME)
        assertTrue(converter.FILE_PATH_REGEX.matches("/longer/path/mainframer/$PROJECT_NAME/Example.kt"))
    }

    @Test
    fun `Should catch file path if it ends with java class name`() {
        val converter = RemoteToLocalInputConverter(PROJECT_NAME)
        assertTrue(converter.FILE_PATH_REGEX.matches("/longer/path/mainframer/$PROJECT_NAME/Example.java"))
    }

    @Test
    fun `Should not catch file path if it ends with undefined class name`() {
        val converter = RemoteToLocalInputConverter(PROJECT_NAME)
        assertFalse(converter.FILE_PATH_REGEX.matches("/longer/path/mainframer/$PROJECT_NAME/Example."))
    }

}

class RemoteToLocalInputConverter(projectName: String) {
    private val FILE_END_PATH_REGEX = "(/.+\\.\\w+)*"
    val FILE_PATH_REGEX = "((/.+)*/mainframer/$projectName$FILE_END_PATH_REGEX)".toRegex()
}
