/**
* JetBrains Space Automation
* This Kotlin-script file lets you automate build activities
* For more info, see https://www.jetbrains.com/help/space/automation.html
*/

job("Clone") {
    container("alpine/git") {
        shellScript {
            content = """
                echo "Ready to go!"
            """
        }
    }
}
