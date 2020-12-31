job("CodeSpyGlass") {
    container("openjdk:11") {
        resources {
            memory = 2048
            cpu = 2048
        }

        env["GITHUB_URL"] = Params("githuburl")
        val codeDirectory = "code"
        val githubUrlShellReference = "${'$'}GITHUB_URL"
        shellScript {
            content = """
                echo "Cloning '$githubUrlShellReference' into directory '$codeDirectory'"
                git --version
                rm -rf $codeDirectory
                git clone $githubUrlShellReference $codeDirectory
                ls -al $codeDirectory
                find $codeDirectory/src -name "*.java"
            """.trimIndent()
        }
    }
}
