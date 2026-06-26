# Skillforge Android App

A Kotlin Android application built as part of the Clickretina Android Developer take-home assessment.

## Tech Stack

* Kotlin
* Jetpack Compose
* MVVM Architecture
* Retrofit
* Kotlin Coroutines
* Kotlinx Serialization
* Coil
* Navigation Compose
* StateFlow

## Features

* Home screen displaying categories and popular courses
* Course Detail screen with instructor information and lesson list
* Lesson screen with video-player style UI
* API integration using Retrofit
* Image loading using Coil
* Loading and error state handling
* Unit test for the repository layer

---

# AI Usage

This project was developed using AI-assisted development as part of the required workflow.

### AI Tools Used

* Gemini (Android Studio)
* Google Antigravity

### Example Prompts

**Prompt 1**

> Set up the project foundation using MVVM architecture with Retrofit, Kotlin Coroutines, Kotlinx Serialization, Navigation Compose, Coil, and a repository pattern. Keep the implementation modular and production-ready.

**Prompt 2**

> Implement the Home screen by matching the provided design as closely as possible. Use the API data to populate categories and popular courses while preserving the existing architecture.

**Prompt 3**

> Implement the Course Detail and Lesson screens to closely match the provided reference designs. Reuse the existing ViewModel and navigation, populate all UI from the API response, and avoid introducing additional functionality beyond the assessment requirements.

### What AI Got Right

AI significantly accelerated the project setup by generating the application structure, networking layer, models, navigation, and Compose UI scaffolding. This allowed me to focus more on reviewing the implementation, integrating the API, and refining the UI.

### What AI Got Wrong

Some generated UI components did not accurately match the provided designs. There were inconsistencies in spacing, typography, colors, and layout alignment. I reviewed these implementations, refined the Compose layouts, adjusted styling, and corrected the UI to better match the assessment screenshots.

---

## Build

Open the project in Android Studio and run:

```bash
./gradlew assembleDebug
```

The generated debug APK is also included in this repository.
