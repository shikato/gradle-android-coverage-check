# gradle-android-coverage-check

AndroidCoverageCheck is Gradle plugin to check [JaCoCo](https://www.eclemma.org/jacoco/) coverage reports.  

![2016-07-31 21 10 03](https://cloud.githubusercontent.com/assets/4592677/17276406/b7f4547a-5763-11e6-8a8a-cbc36b53ea8a.png)
 
## Getting started
**build.gradle**  

```groovy
buildscript {
  repositories {
    maven {
      url "https://plugins.gradle.org/m2/"
    }
  }
  dependencies {
    classpath "gradle.plugin.org.shikato.gradle.android.coverage.check:gradle-android-coverage-check:1.0.0"
  }
}

apply plugin: "org.shikato.gradle.android.coverage.check"
```

cf. https://plugins.gradle.org/plugin/org.shikato.gradle.android.coverage.check

## Usage

### Task
* androidCovrageCheck - Check JaCoCo coverage reports.  

#### e.g.
##### Create coverage reports & check
```
./gradlew createDebugCoverageReport androidCoverageCheck  
```
cf. [createDebugCoverageReport](https://developer.android.com/studio/releases/gradle-plugin)

##### Check coverage reports only
```
./gradlew androidCoverageCheck  
```

### Options
**build.gradle**

```groovy
// to exclude files
// Default: []
String[] excludeFiles = ["**/*Activity.java",
                         "**/*Fragment.java",
                         "package/name/**/Shikato2.java"];

// paths of coverage reports 
// Default: ["**/coverage/**/report.xml"]
String[] reportXmlPath = ["hoge/fuga/**/report.xml"];

androidCoverageCheck {
    // Set excludeFiles variable
    excludes excludeFiles
    
    // Set reportXmlPath variable
    reportXml reportXmlPath

    // Minimum threshold of INSTRUCTION
    // Default: 20
    instruction 50

    // Minimum threshold of BRANCH
    // Default: 20
    branch 50

    // If the threshold cannot be met, this option will cause the build to fail.
    // Default: true
    isBuildFailure false
}

```

## Other documents
[Qiita](http://qiita.com/shikato/items/9869719ab5e22ee9d061)

## License
MIT
