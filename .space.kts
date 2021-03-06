// The Space instance is at http://codespyglass.jetbrains.space/
job("CodeSpyGlass") {
    container("openjdk:11") {
        resources {
            memory = 2048
            cpu = 2048
        }

        env["GITHUB_URL"] = Params("githuburl")
        val codeDirectory = "code"
        val githubUrlShellReference = "${'$'}GITHUB_URL"
        val javaFilesFile = "javaFiles.txt"
        shellScript {
            content = """
                echo "=== Started pipeline ==="
                echo "Cloning '$githubUrlShellReference' into directory '$codeDirectory'"
                git --version
                rm -rf $codeDirectory
                git clone $githubUrlShellReference $codeDirectory
                echo "=== Cloned the repository ==="
                ls -al $codeDirectory
                ((find $codeDirectory/src/main -name "*.java" || \ 
                find "$codeDirectory"/src -name "*.java" || \
                echo "FAILED: Couldn't find source root within the '$codeDirectory' directory. Tried 'src/main', then 'src'.") \
                2>&1 | grep -v ": No such file or directory") > $javaFilesFile
                echo "=== Found Java files ==="
                cat $javaFilesFile
            """.trimIndent()
        }
    }
}
