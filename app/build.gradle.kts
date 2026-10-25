import java.util.Properties

plugins {
    id("com.android.application")
}

// Lee las credenciales del correo desde local.properties (no se sube al repositorio)
val localProps = Properties().apply {
    val archivo = rootProject.file("local.properties")
    if (archivo.exists()) archivo.inputStream().use { load(it) }
}

fun prop(clave: String): String = localProps.getProperty(clave, "")

android {
    namespace = "com.cursoandroid.petagram"
    compileSdk = 37

    defaultConfig {
        applicationId = "com.cursoandroid.petagram"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        buildConfigField("String", "MAIL_USUARIO", "\"${prop("mail.usuario")}\"")
        buildConfigField("String", "MAIL_PASSWORD", "\"${prop("mail.password")}\"")
        buildConfigField("String", "MAIL_DESTINO", "\"${prop("mail.destino")}\"")
    }

    buildFeatures {
        buildConfig = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    // JavaMail trae archivos de licencia duplicados; sin esto el build falla
    packaging {
        resources {
            excludes += setOf(
                "META-INF/NOTICE.md",
                "META-INF/LICENSE.md",
                "META-INF/NOTICE",
                "META-INF/LICENSE",
                "META-INF/DEPENDENCIES"
            )
        }
    }
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("androidx.cardview:cardview:1.0.0")
    implementation("androidx.viewpager:viewpager:1.0.0")
    implementation("androidx.coordinatorlayout:coordinatorlayout:1.2.0")

    // JavaMail para Android
    implementation("com.sun.mail:android-mail:1.6.7")
    implementation("com.sun.mail:android-activation:1.6.7")
}
