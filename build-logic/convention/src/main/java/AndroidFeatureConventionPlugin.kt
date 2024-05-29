import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidFeatureConventionPlugin : Plugin<Project> {
  override fun apply(target: Project) {
    with(target) {
      pluginManager.apply {
        apply("build.android.library")
        apply("build.android.library.compose")
        apply("build.android.hilt")
        apply("org.jetbrains.kotlin.plugin.compose")
      }

      val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

      dependencies {
        add("implementation", project(":core:designsystem"))
        add("implementation", project(":core:domain"))

        add("implementation", libs.findLibrary("kotlinx.coroutines").get())
        add("implementation", libs.findLibrary("androidx.lifecycle").get())
      }
    }
  }
}
