// Cấu hình buildscript để sử dụng phiên bản Kotlin mới nhất
buildscript {
    val kotlin_version = "2.1.0"  // Cập nhật phiên bản Kotlin ở đây

    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        // Thêm plugin Kotlin vào dependencies
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
    }
}

// Cấu hình các repository cho toàn bộ dự án
allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

// Đảm bảo thư mục build của dự án là thư mục tương đối
val newBuildDir: Directory = rootProject.layout.buildDirectory.dir("../../build").get()
rootProject.layout.buildDirectory.value(newBuildDir)

// Cấu hình thư mục build cho tất cả các subprojects
subprojects {
    val newSubprojectBuildDir: Directory = newBuildDir.dir(project.name)
    project.layout.buildDirectory.value(newSubprojectBuildDir)
}

// Cấu hình phụ thuộc giữa các subproject, đảm bảo app được xây dựng trước
subprojects {
    project.evaluationDependsOn(":app")
}

// Định nghĩa tác vụ 'clean' để xóa các tệp build
tasks.register<Delete>("clean") {
    delete(rootProject.layout.buildDirectory)
}
