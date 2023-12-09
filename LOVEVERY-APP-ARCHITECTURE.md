# Lovevery Notes


### App overview

Given the existing REST API endpoints, the intention of the app is to let the users create notes about other users on specific subjects.

This app can be used, for example, by a school teacher who wants to take notes about her/his students.
The functionality is described below:


1. The user opens the app
2. If there are no notes for any user, a screen to register a user will be shown
3. Then, a subject needs to be captured
4. Then, a screen to send notes about the new user and a specific subject will be displayed

In the diagram below, the suggested navigation is demonstrated.
It was created with [Figma](www.figma.com) 

![lovevery-notes-diagram](https://github.com/lalongooo/lovevery-notes/assets/1671260/28edc3db-19d3-4f01-aef4-cd706e229dfa)

Next, you can find screenshots of all the screens of the app.
I created the screenshots using the Device Art Generator from [android.com](https://developer.android.com/distribute/marketing-tools/device-art-generator):

![lovevery-notes-pixel-6-pro](https://github.com/lalongooo/lovevery-notes/assets/1671260/dbb45b40-7406-4ce6-9a7d-66f212968d41)


### Architecture of the app

The app was build using the following tools/libraries/frameworks:

- LiveData: For data observability from Android Fragments.
- ViewModel: It is a class that survives Android device configuration changes
- In conjuction, LiveData & ViewModel, help you achieve best practices suggested by the MVVM (Model-View-ViewModel)
- I used [Retrofit](https://square.github.io/retrofit/) to model the REST API
- Behind the scenes, Retrofit uses [OkHttp](https://square.github.io/okhttp/) to perform the HTTP communication
- For data serialization Retrofit has an adapter that let me parse the JSON response into Kotlin data classes
- Such adapter, uses [Gson](https://github.com/google/gson) to serialize JSON into Kotlin data classes
- Android has imposed limitations on which thread network request run on, that means, every HTTP request should be executed in a background thread
- So, I use RxJava to execute network request processes in a background thread.
- The [`applySchedulers()`](https://github.com/lalongooo/lovevery-notes/blob/main/lovevery-data/src/main/java/com/lovevery/notes/android/data/extensions/Single.kt) extension function is the key component to switch between different threads

#### Unit testing

I used [JUnit4](https://junit.org/junit4/) and [Mockk](https://mockk.io/) to perform unit tests.

Those unit tests can be found in [this package](https://github.com/lalongooo/lovevery-notes/tree/main/app/src/test/java).



#### Automated testing

Automated testing is a wide concept, so I will focus on 2 key aspects:

1. **Unit testing**: It involves having unit tests for all of the clases that contain critical business logic of the app. They should verify the correct behavior of every function in a class, they are even intended to validate every if/else clause within the code.

    So, that's what I'd encourage for this project, have unit tests for most of the classes which will run automatically on every commit push.
    That's why I added a [GitHub Action workflow file](https://github.com/lalongooo/lovevery-notes/blob/main/.github/workflows/build_and_test.yml) that builds and runs all the unit tests of the app. You can see all of the Github Actions executions [here](https://github.com/lalongooo/lovevery-notes/actions).

    ..and I consider this, a type of automated testing.
   

2. **Integration or instrumentation testing**: It involves running tests that simulate user interaction with the app.

    These tests are usually slow because they need to run on an Android emulator and for these type of automated testing, we have 2 options:
    - [**Espresso**](https://developer.android.com/training/testing/espresso): This is the native way to execute instrumentation tests on an Android device.

      Since we we'll have total control of the Android app's code, I'd suggest to have Espresso tests at least for the business-critical functions of the app since this type of tests are hard to maintain and they're usually slow.
      If we have chance to have more and more espresso tests as we develop features, I'd go that way.

    - [**Appium**](https://appium.io): I've worked with Appium before and I encourage to use Appium when you have a dedicated team that works on automated testing for more than one platform.

      If we'll have complete ownership of the Android code, then I don't recommend to spend time writing tests with Appium.
      Appium becomes difficult to work with because:

      - You need to coordinate both, Android & iOS, teams to have the same IDs for the UI components. For example, `login_button` should be the same on Android & iOS for Appium scritps to work.
      - Having the communication between both teams to have the same IDs for UI components is hard and requires lots of people to get involved
      - It is very easy to break Appium scripts when new screens are added or modified due to the IDs
      - You need an additional DevOps engineer to help troubleshoot issues on the server on which Appium scripts run

#### Tradeoffs made

- The app makes requests too often because it needs to have up-to-date data and the nataure of the REST API it depends on
- Everything the users enters is kept on memory, so if the user kills the app, everything will be lost. It will require a new request to the REST API to have data available again
- The data is too simple to be modularized, however if features for the app grow, I'd modularize the features in the same way I have the `lovevery-data` module
- Having the project modularized speeds-up Gradle builds substantially, that's why I created `lovevery-data` module and will encourage to do the same as features of the app grow
- UI is not perfect, it is too simple and uses the native UI components of the Android OS, I would like to have [Material Design](https://m2.material.io/develop/android) incorporated
- In order to prevent too much request to the server, I'd like to have a more complex data layer, which would take care of having data in sync with the server. This is more specifically for the "Send" button, as of now, it is blocked until a message/note is successfully sent.
