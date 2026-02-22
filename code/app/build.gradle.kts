plugins {
    alias(libs.plugins.android.application)
}

android {
    tasks.withType<Test>{
        useJUnitPlatform()
    }
    namespace = "com.example.code"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.example.code"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

tasks.register<Javadoc>("generateReleaseJavadoc") {
    val ext = project.extensions.getByType<com.android.build.gradle.AppExtension>()
    source = project.files(ext.sourceSets["main"].java.srcDirs).asFileTree
    setDestinationDir(file("${rootProject.rootDir.parentFile}/doc/javadoc"))
    exclude("**/R.java", "**/BuildConfig.java")
    options {
        encoding = "UTF-8"
    }
    doFirst {
        val releaseVariant = ext.applicationVariants.first { it.name == "release" }
        classpath = project.files(releaseVariant.javaCompileProvider.get().classpath.files) +
                project.files(ext.bootClasspath)
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    testImplementation("org.junit.jupiter:junit-jupiter-api:6.0.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:6.0.2")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:6.0.2")

    implementation(files("/Users/youhaozhao/Library/Android/sdk/platforms/android-36/android.jar"))
}