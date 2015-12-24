# gradle-android-coverage-check

AndroidCoverageCheck is a gradle plugin to check coverage reports.  

![demo1](https://qiita-image-store.s3.amazonaws.com/0/47437/27441815-8d99-66cd-c214-02ff383c1ce8.png)
 
## Install
**build.gradle**  

Add a dependency.
```groovy
buildscript {
    repositories {
        maven {
            url 'https://github.com/shikato/gradle-android-coverage-check/raw/master/repository'
        }
    }
    dependencies {
        classpath 'org.shikato.gradle.android.coverage.check:android-coverage-check:0.0.2'
    }
}
```
Apply this plugin.
```groovy
apply plugin: 'android-coverage-check'
```

## Usage

### Task
* androidCovrageCheck - Checks coverage reports.  

#### Examples
**Create coverage reports & check**
```
./gradlew createDebugCoverageReport androidCoverageCheck  
```

**Only check**  

report.xml should have already existed.
```
./gradlew androidCoverageCheck  
```

### Options
**build.gradle**

```groovy
// Excluded targets
// Default: []
String[] excludeFiles = ["**/*Activity.java",
                         "**/*Fragment.java",
                         "package/name/**/Shikato2.java"];

// coverage reports path
// If reports are plural, The task checks each.
// Default: ["**/coverage/**/report.xml"]
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
    excludes excludeFiles
    // set coverage report path
    reportXml reportXmlPath
}

```

## Documents
[Qiita](http://qiita.com/shikato/items/9869719ab5e22ee9d061)

## License
MIT
