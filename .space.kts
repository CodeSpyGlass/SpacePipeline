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
        val githubUrl = JobEnvironmentVariable(
            env, Params("githuburl"), "GITHUB_URL"
        )
        shellScript {
            content = """
                echo "Cloning ${githubUrl.ref()}"
                git --version
                rm -rf code
                git clone ${githubUrl.ref()} code
                ls -al code
            """
        }
    }
}
