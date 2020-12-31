job("CodeSpyGlass") {
    container("openjdk:11") {
        class JobEnvironmentVariable(
            private val environmentVariableReference: String,
            pipelineParameterName: String
        ) {
            init {
                env[environmentVariableReference] =
                    Params(pipelineParameterName)
            }

            /**
             * A reference which can be used when constructing shell
             * commands, such as `echo "$MY_ENVIRONMENT_VARIABLE"`
             *
             * This method returns "$GITHUB_URL" for the environment variable
             * "GITHUB_URL".
             */
            fun shellReference() = "${'$'}$environmentVariableReference"
        }

        abstract class PipelineStage {
            abstract fun run()
        }

        class CloneStage(
            private val githubUrl: JobEnvironmentVariable,
            private val codeDirectory: String
        ) : PipelineStage() {
            override fun run() {
                shellScript {
                    content = """
                echo "Cloning '${githubUrl.shellReference()}' into directory '$codeDirectory'"
                git --version
                rm -rf $codeDirectory
                git clone ${githubUrl.shellReference()} $codeDirectory
                ls -al $codeDirectory
            """.trimIndent()
                }
            }
        }

        class AnalysisStage(
            private val codeDirectory: String
        ) : PipelineStage() {
            override fun run() {
                shellScript {
                    content = """
                        echo "Analysing Java in the directory '$codeDirectory'"
                    """.trimIndent()
                }
            }
        }

        val githubUrl = JobEnvironmentVariable("GITHUB_URL", "githuburl")
        listOf(
            CloneStage(githubUrl, "code"),
            AnalysisStage("code")
        ).forEach(PipelineStage::run)
    }
}
