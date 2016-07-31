# gradle-android-coverage-check

AndroidCoverageCheck is a gradle plugin to check [JaCoCo](http://www.eclemma.org/jacoco/) coverage reports.  

![2016-07-31 21 10 03](https://cloud.githubusercontent.com/assets/4592677/17276406/b7f4547a-5763-11e6-8a8a-cbc36b53ea8a.png)
 
## Download
**build.gradle**  

Build script snippet for use in all Gradle versions:
```groovy
buildscript {
  repositories {
    maven {
      url "https://plugins.gradle.org/m2/"
    }
  }
  dependencies {
    classpath "gradle.plugin.org.shikato.gradle.android.coverage.check:gradle-android-coverage-check:0.0.7"
  }
}

apply plugin: "org.shikato.gradle.android.coverage.check"
```

Build script snippet for new, incubating, plugin mechanism introduced in Gradle 2.1:
```
plugins {
  id "org.shikato.gradle.android.coverage.check" version "0.0.7"
}
```
https://plugins.gradle.org/plugin/org.shikato.gradle.android.coverage.check

## Usage

### Task
* androidCovrageCheck - Check JaCoCo coverage reports.  

#### Examples
##### Create coverage reports & check
Only at the time of "org.gradle.parallel=false".
```
./gradlew createDebugCoverageReport androidCoverageCheck  
```

##### Only check
If report.xml already exists.
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

// Coverage reports path
// If reports are plural, The task each checks.
// Default: ["**/coverage/**/report.xml"]
String[] reportXmlPath = ["hoge/fuga/**/report.xml"];

androidCoverageCheck {
    // If there are unsatisfied coverages, this option will make a build failure.
    // Default: true
    isBuildFailure false

    // Minimum threshold of INSTRUCTION
    // Default: 20
    instruction 50

    // Minimum threshold of BRANCH
    // Default: 20
    branch 50

    // Set excluded targets
    excludes excludeFiles
    // Set coverage report path
    reportXml reportXmlPath
}

```

## Other documents
[Qiita](http://qiita.com/shikato/items/9869719ab5e22ee9d061)

## License
MIT
