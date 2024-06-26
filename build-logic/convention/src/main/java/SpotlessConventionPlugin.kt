import com.diffplug.gradle.spotless.SpotlessExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class SpotlessConventionPlugin : Plugin<Project> {
  override fun apply(target: Project) {
    with(target) {
      pluginManager.apply("com.diffplug.spotless")

      extensions.configure<SpotlessExtension> {
        kotlin {
          val buildDirectory = layout.buildDirectory.asFileTree
          target("**/*.kt")
          targetExclude("**/build/**/*.kt")
          target("**/*.kt")
          targetExclude(buildDirectory)
          ktlint().editorConfigOverride(
            mapOf(
              "indent_size" to "2",
              "continuation_indent_size" to "2"
            )
          )
          licenseHeaderFile(rootProject.file("spotless/license.kt"))
          trimTrailingWhitespace()
          endWithNewline()
        }
      }
    }
  }
}
