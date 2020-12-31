job("CodeSpyGlass") {
    container("alpine/git") {
        env["GITHUB_URL"] = Params("githuburl")
        val githubUrlEnvironmentVariable = "${'$'}GITHUB_URL"
        shellScript {
            content = """
                echo "Cloning $githubUrlEnvironmentVariable"
                git --version
                rm -rf code
                git clone $githubUrlEnvironmentVariable code
                ls -al code
            """
        }
    }
}
