class JobEnvironmentVariable(
    environment: Environment,
    environmentVariableValue: ParamContext,
    private val environmentVariableName: String
) {
    init {
        environment[environmentVariableName] = environmentVariableValue
    }

    fun ref(): String = "${'$'}${environmentVariableName}"
}

job("CodeSpyGlass") {
    container("alpine/git") {
        val githubUrlEnvironmentVariableName = "GITHUB_URL"
        env[githubUrlEnvironmentVariableName] = Params("githuburl")
        val githubUrlEnvironmentVariableRef = "${'$'}GITHUB_URL"
        shellScript {
            content = """
                echo "Cloning $githubUrlEnvironmentVariableRef"
                git --version
                rm -rf code
                git clone $githubUrlEnvironmentVariableRef code
                ls -al code
            """
        }
    }
}
