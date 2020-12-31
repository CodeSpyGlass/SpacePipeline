job("CodeSpyGlass") {
    container("alpine/git") {
        env["GITHUB_LINK"] = Params("githublink")
        shellScript {
            content = """
                echo "Cloning ${'$'}GITHUB_LINK"
            """
        }
    }
}
