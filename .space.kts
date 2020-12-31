job("CodeSpyGlass") {
    container("openjdk:11") {
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
            """.trimIndent()
        }
        kotlinScript { api ->
            val githubUrl = api.parameters["githubUrl"]
            println("githubUrl is: $githubUrl")
        }
    }
}
