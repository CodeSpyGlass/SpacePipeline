job("CodeSpyGlass") {
    container("alpine/git") {
        env["GITHUB_URL"] = Params("githuburl")
        shellScript {
            content = """
                echo "Cloning ${'$'}GITHUB_URL"
                git --version
                rm -rf code
                git clone ${'$'}GITHUB_URL code
                ls -al code
            """
        }
    }
}
