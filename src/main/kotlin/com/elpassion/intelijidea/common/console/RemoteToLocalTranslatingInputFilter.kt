package com.elpassion.intelijidea.common.console

import com.intellij.execution.filters.InputFilter
import com.intellij.execution.ui.ConsoleViewContentType
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Pair
import java.util.regex.Pattern

class RemoteToLocalTranslatingInputFilter(private val project: Project) : InputFilter {

    private val pattern = Pattern.compile("$FILE_REGEX:$LINE_REGEX: $ERROR_REGEX", Pattern.MULTILINE)

    override fun applyFilter(text: String, contentType: ConsoleViewContentType): MutableList<Pair<String, ConsoleViewContentType>> {
        return mutableListOf(Pair(translateText(text), contentType))
    }

    private fun translateText(text: String): String {
        val matcher = pattern.matcher(text)
        return if (!matcher.find()) {
            text
        } else {
            text.replace("\\D+mainframer/${project.name}".toRegex(), project.basePath!!)
        }
    }

    companion object {
        private val FILE_REGEX = "(^|[\\W])(?<file>(?:\\p{Alpha}\\:|/)[0-9 a-z_A-Z\\-\\\\./]+)"
        private val LINE_REGEX = "(?<line>[0-9]+)"
        private val ERROR_REGEX = "error: \\D+"
    }
}