# gradle-android-coverage-check

Gradle-android-coverage-check is a gradle plugin to 

![demo1]()
 
 
## Install
build.gradle
```
```
## Usage

### Task
Run ./gradlew tasks on your project root to see all available tasks.
* androidCovrageCheck - Checks coverage reports.  

### Examples
**Create a coverage reports & check**

```
./gradlew createDebugCoverageReport androidCoverageCheck  
```

**Only heck**
report.xml should have already existed.
```
./gradlew androidCoverageCheck  
```


## License
MIT
