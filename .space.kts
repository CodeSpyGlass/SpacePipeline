job("CodeSpyGlass") {
    container("alpine/git") {
        val githubLink: String = Params("githublink")
        shellScript {
            content = """
                echo "Cloning ${githubLink}"
            """
        }
    }
}
