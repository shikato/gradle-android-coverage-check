# gradle-android-coverage-check

Gradle-android-coverage-check is a gradle plugin to check a coverage reports.  

![demo1](https://qiita-image-store.s3.amazonaws.com/0/47437/27441815-8d99-66cd-c214-02ff383c1ce8.png)
 
## Install
**build.gradle**  

Please, add a dependency.
```groovy
repositories {
    maven {
        url 'https://github.com/shikato/gradle-android-coverage-check/raw/master/repository'
    }
}

dependencies {
    classpath 'org.shikato.gradle.android.coverage.check:android-coverage-check:0.0.1'
}
```
Please, apply this plugin.
```groovy
apply plugin: 'android-coverage-check'
```

## Usage

### Task
* androidCovrageCheck - Checks coverage reports.  

#### Examples
**Create a coverage reports & check**
```
./gradlew createDebugCoverageReport androidCoverageCheck  
```

**Only check**  

report.xml should have already existed.
```
./gradlew androidCoverageCheck  
```

### Options

```groovy
// Excluded targets
// Default: []
String[] excludeFiles = ["**/*Activity.java",
                         "**/*Fragment.java",
                         "package/name/**/Shikato2.java"];

// coverage reports path
// If reports are plural, The task checks each.
// Default: **/coverage/**/report.xml
String[] reportXmlPath = ["hoge/fuga/**/report.xml"];

androidCoverageCheck {
    // If there are unsatisfied coverages, this option will make a build failure.
    // Default: true
    isBuildFailure false

    // minimum threshold of INSTRUCTION
    // Default: 20
    instruction 50

    // minimum threshold of BRANCH
    // Default: 20
    branch 50

    // set excluded targets
    excludes excludeFiles;
    // set coverage report path
    xmlReports reportXmlPath
}

```

## License
MIT
