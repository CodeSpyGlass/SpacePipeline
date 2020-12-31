job("CodeSpyGlass") {
    container("alpine/git") {
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
            private val githubUrl: JobEnvironmentVariable
        ) : PipelineStage() {
            override fun run() {
                shellScript {
                    content = """
                echo "Cloning ${githubUrl.shellReference()}"
                git --version
                rm -rf code
                git clone ${githubUrl.shellReference()} code
                ls -al code
            """
                }
            }
        }

        val githubUrl = JobEnvironmentVariable("GITHUB_URL", "githuburl")
        listOf(
            CloneStage(githubUrl)
        ).forEach(PipelineStage::run)
    }
}
