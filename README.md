<p align="center">      
📞 Jetpack Compose dial button library<br>
UI for selecting targets by dragging like dialing a phone number
</p>

## Preview
<p align="center">
<img src="preview/row_dial.gif" width="270"/>
<img src="preview/column_dial.gif" width="270"/>
</p>

## download
[![](https://jitpack.io/v/panax11/DialButton.svg)](https://jitpack.io/#panax11/DialButton)

### gradle

[settings.gradle.kts](https://github.com/panax11/DialButton/blob/main/settings.gradle.kts)
```gradle
dependencyResolutionManagement {
		repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
		repositories {
			mavenCentral()
			maven { url 'https://jitpack.io' }
		}
	}
```
[build.gradle.kts](https://github.com/panax11/DialButton/blob/main/app/build.gradle.kts)
```gradle
dependencies {
	        implementation 'com.github.panax11:DialButton:1.0'
	}
```

## Usage

[Sample](https://github.com/panax11/DialButton/blob/main/app/src/main/java/com/panax/dialbuttton/Sample.kt)

Add dial controller
```kotlin
val controller = rememberDialController()
```
Add a dialLayout modifier to the top-level composable to use as a dial
```kotlin
Column(
        modifier = modifier.dialLayout(controller = controller)
)
```

Add a dialControl modifier to the composable to use as a dial controller
```kotlin
SampleController(
    modifier = Modifier
        .columnDialControl(
            controller = controller,
            onTargetSelect = { i, onDragEnd -> }
        )
)
```

Add a dialItem modifier to the composable to use as a dial item
```kotlin
SampleItem(
    modifier = Modifier
        .dialItem(controller = controller, index = index),
)
```
