package com.elpassion.intelijidea

import com.elpassion.intelijidea.util.mfFilename
import com.intellij.execution.configurations.CommandLineState
import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.execution.process.OSProcessHandler
import com.intellij.execution.process.ProcessHandler
import com.intellij.execution.runners.ExecutionEnvironment

class MFCommandLineState(private val executionEnvironment: ExecutionEnvironment,
                         private val mainframerPath: String,
                         private val buildCommand: String,
                         private val taskName: String) : CommandLineState(executionEnvironment) {

    override fun startProcess(): ProcessHandler =
            OSProcessHandler(createCommandLine().withWorkDirectory(executionEnvironment.project.basePath))

    private fun createCommandLine() =
            GeneralCommandLine("bash", "$mainframerPath/$mfFilename", "$buildCommand $taskName")
}