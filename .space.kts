job("CodeSpyGlass") {
    container("openjdk:11") {
        fun cloneStage(githubUrlShellReference: String, codeDirectory: String) {
            shellScript {
                content = """
                echo "Cloning '$githubUrlShellReference' into directory '$codeDirectory'"
                git --version
                rm -rf $codeDirectory
                git clone $githubUrlShellReference $codeDirectory
                ls -al $codeDirectory
            """.trimIndent()
            }
        }

        fun analysisStage(codeDirectory: String) {
            shellScript {
                content = """
                        echo "Analysing Java in the directory '$codeDirectory'"
                    """.trimIndent()
            }
        }

        env["GITHUB_URL"] = Params("githuburl")
        val codeDirectory = "code"
        cloneStage("${'$'}GITHUB_URL", codeDirectory)
        analysisStage(codeDirectory)
    }
}
