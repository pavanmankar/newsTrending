# MVVM News Application
###  A News Application that allows users to fetch news built by following MVVM Architecture and Jetpack Components with Unit and Instrumentation Testing.



## Using Library in your Android application

Update your settings.gradle file with the following dependency.

```groovy
dependencyResolutionManagement {
		repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
		repositories {
			mavenCentral()
			maven { url 'https://jitpack.io' }
		}
	}
```

Update your module level build.gradle file with the following dependency.

```groovy
dependencies {
    implementation 'com.github.pavanmankar:newsTrending:Tag'
}
```
Do not forget to add internet permission in manifest if already not present

```
<uses-permission android:name="android.permission.INTERNET" />
```

## Major Highlights

- MVVM Architecture
- Kotlin
- Dagger Hilt
- Retrofit
- Coroutines
- Flows
- Stateflow
- Viewbinding
- Unit Test

  ## Features Implemented

- Fetching News
- Top Headlines News
- News Based on Source
- News Based on Single/Multi Country Selection
- News Based on Single/Multi Language Selection
- Instant Search using Flows Operator
  * Debounce
  * Filter
  * DistinctUntilChanged
  * FlatMapLatest
- Unit Test
  - Mockito
  - JUnit
  - [Turbine](https://github.com/cashapp/turbine/)
