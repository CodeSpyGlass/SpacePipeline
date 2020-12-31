job("CodeSpyGlass") {
    container("alpine/git") {
        env["GITHUB_LINK"] = Params("githublink")
        shellScript {
            content = """
                echo "Cloning ${'$'}GITHUB_LINK"
                git --version
                rm -rf code
                git clone ${'$'}GITHUB_LINK code
                ls -al code
            """
        }
    }
}
