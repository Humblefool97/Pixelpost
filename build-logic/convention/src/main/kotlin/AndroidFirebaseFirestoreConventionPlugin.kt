import com.pixelpost.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidFirebaseFirestoreConventionPlugin : Plugin<Project>{
    override fun apply(target: Project) {
      with(target){
          dependencies{
              val bom = libs.findLibrary("firebase-bom").get()
              "implementation"(platform(bom))
              "implementation"(libs.findLibrary("firebase-firestore").get())
          }
      }
    }
}