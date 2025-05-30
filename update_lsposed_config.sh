#!/data/data/com.termux/files/usr/bin/bash

echo "🔧 Menambahkan plugin repository dan memperbaiki konfigurasi Gradle..."

# Update build.gradle.kts (root project)
cat > build.gradle.kts <<EOF
pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "HarpaLogger"
include(":app")
EOF

# Update gradle.properties
echo "org.gradle.jvmargs=-Xmx2048m" > gradle.properties

# Pastikan file settings.gradle.kts mengandung include(":app")
grep -q 'include(":app")' settings.gradle.kts || echo 'include(":app")' >> settings.gradle.kts

echo "✅ Konfigurasi Gradle telah diperbarui."
