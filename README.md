# Stytch Android SDK

## Table of contents

* [Overview](#overview)
* [Requirements](#requirements)
* [Installation](#installation)
* [Getting Started](#getting-started)
  * [Configuration](#configuration)
  * [Starting UI Flow](#starting-ui-flow)
  * [Starting Custom Flow](#starting-custom-flow)

## Overview

Stytch's SDKs make it simple to seamlessly onboard, authenticate, and engage users. Improve security and user experience with passwordless authentication.

## Permissions

Open your app's `AndroidManifest.xml` file and add the following permission.

```xml
<uses-permission android:name="android.permission.INTERNET" />
```

## Requirements

The SDK supports API level 21 and above ([distribution stats](https://developer.android.com/about/dashboards/index.html)).

- minSdkVersion = 21
- Android Gradle Plugin 4.1.1
- AndroidX

## Installation

```gradle
repositories {
   maven()
}
```

Add stytch-android to your build.gradle dependencies.

```app-gradle

android {
      compileOptions {
            sourceCompatibility JavaVersion.VERSION_1_8
            targetCompatibility JavaVersion.VERSION_1_8
      }
      kotlinOptions {
            jvmTarget = '1.8'
      }
}
            
dependencies {
    implementation 'com.stytch.sdk:sdk:0.2.0'
}
```

## Getting Started

### Configuration

Pick a unique URL scheme for redirecting the user back to your app.
For this example, we'll use YOUR_APP_NAME://.
To start using Stytch, you must configure it:

```
    Stytch.instance.configure(
        PROJECT_ID,
        SECRET,
        YOUR_APP_NAME,
        HOST
    )
```

You can specify Stytch environment `TEST` or `LIVE`:

```
    Stytch.instance.environment = StytchEnvironment.TEST
```

You can specify Stytch loginMethod `LoginOrSignUp` (default) or `LoginOrInvite`:
`LoginOrSignUp`  - Send either a login or sign up magic link to the user based on if the email is associated with a user already. 
`LoginOrInvite` - Send either a login or invite magic link to the user based on if the email is associated with a user already. If an invite is sent a user is not created until the token is authenticated. 
```
Stytch.instance.loginMethod = StytchLoginMethod.LoginOrInvite
```

Add this in your AndroidManifest.xml.
For StytchUI ACTIVITY_NAME = "com.stytch.sdk.ui.StytchMainActivity"

```
<activity android:name="ACTIVITY_NAME"
    android:theme="@style/Theme.StytchTheme"
    android:launchMode="singleTask">

    <intent-filter android:label="stytch_sdk_deep_link">
        <action android:name="android.intent.action.VIEW" />
        <category android:name="android.intent.category.DEFAULT" />
        <category android:name="android.intent.category.BROWSABLE" />
        <data android:scheme=<YOUR_APP_SCHEME>
            android:host="stytch.com" />
    </intent-filter>
</activity>
```

### Starting UI Flow

#### Show UI

Setup StytchUICustomization and StytchUI.StytchUIListener before starting StytchMainActivity

```
    //Set  StytchUI.StytchUIListener
    StytchUI.instance.uiListener = this

    //Set StytchUICustomization
    StytchUI.instance.uiCustomization = createCustomization()

    //Start StytchMainActivity
    val intent = Intent(this, StytchMainActivity::class.java)
    startActivity(intent)
```

#### UI Customization

StytchUICustomization() creates Default Customization, to change theme use its parameters

StytchUICustomization
- `titleStyle: StytchTextStyle` - Title text style
- `showTitle: Boolean` - Show/hide title
- `subtitleStyle: StytchTextStyle` - Subtitle text style
- `showSubtitle: Boolean` - Show/hide subtitle
- `inputCornerRadius: Float` - Input corner radius, size in pixels
- `inputBackgroundBorderColor: StytchColor` - Input border color
- `inputBackgroundColor: StytchColor` - Input background color
- `inputHintStyle : StytchTextStyle` - Input hint text style
- `inputTextStyle: StytchTextStyle` - Input text style
- `buttonTextStyle: StytchTextStyle` - Action button text style
- `buttonBackgroundColor : StytchColor` - Action button background color
- `buttonCornerRadius: Float` - Action button corner radius, size in pixels
- `showBrandLogo: Boolean` - Show/hide brand logo
- `backgroundColor : StytchColor` - Window background color

StytchColor
- `StytchColor.fromColor(color: Int)` - create StytchColor from Color Int
- `StytchColor.fromColorId(colorInt: Int)` - create StytchColor from Color Resource Id

StytchTextStyle
- `size: Float` - Text size in pixels
- `color: StytchColor` - Text Color
- `font: Typeface?` - Text font

##### Handle UI Callbacks

StytchUI.StytchUIListener provides callbacks methods
- `onEvent` - called after user found or created
- `onSuccess` - calls after successful user authorization
- `onFailure` - called when invalid configuration

```
StytchUI.StytchUIListener{
    override fun onSuccess(result: StytchResult) {
        Log.d(TAG,"onSuccess: $result")
    }

    override fun onFailure() {
        Log.d(TAG,"onFailure: Oh no")
    }
    
    override fun onEvent(event: StytchEvent) {
        Log.d(TAG,"Event Type: ${event.type}")
        Log.d(TAG,"Is user created: ${event.created}")
        Log.d(TAG,"User ID: ${event.userId}")
    }
}
```

### Starting Custom Flow


Handle deep link Intent with Stytch.instance.handleDeepLink
```
override fun onNewIntent(intent: Intent?) {
    val action: String? = intent?.action
    val data = intent?.data ?: return

    if (action == Intent.ACTION_VIEW) {
        if(Stytch.instance.handleDeepLink(data)) {
            showLoading()
            return
        }
    }
    super.onNewIntent(intent)
}
```

First set listener to Stytch
```
Stytch.instance.listener = this  // Stytch.StytchListener
```

Call login to request login
```
Stytch.instance.login(email)
```

#### Handle Callbacks

Stytch.StytchListener
- onSuccess(result: StytchResult) // Called after successful user authorization. Flow is finished.
- onFailure(error: StytchError) // Called when error occurred, you need to show error to user
- onMagicLinkSent(email: String) // Called after magic link sent to email
