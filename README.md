# Project Title
* TodoApp

# Project Description
TodoApp is my personal assignment to learn basic to advanced concepts through Android app development.
Developed using kotlin and java language for backend operations. For UI designing, used XML.

Used Room database to store user information.
Used SharedPreferences to handle session management. Used Jetpack Android architecture components: LiveData and ViewModel
Some screens are coded in kotlin.

## 🚀 Features

* 📡 SignIn and SignUp features.
* 💹 Dynamically add task, edit tasks and delete tasks.
* 🧩 Change theme from dark to light theme or vice-versa using option menu.
* ⚠️ Used room database for storing task details such as task title, and date and time of scheduled task.
*    Also
* 🎨 Built UI with **XML Layouts** with **Material 3 guidelines**.
* 🧱 Follows **MVVM** principles for scalability and testability.
* 🧪 Unit tests using JUnit and UI Instrumentation using Espresso is integrated.
* Security such as preventing taking screenshots is handled for sensitive screens of app, proguard enabled for avoiding reverse engineering process 

## 🏗️ Architecture Overview
# MVVM-  Model View ViewModel

Each feature in the app is built as a separate module to improve **maintainability, scalability, and build performance**.

**Layered Responsibility:**

| Layer         | Description                                                      |
|---------------|------------------------------------------------------------------|
| **Model**     | Room database implementation, Data store for session management. |
| **View**      | XML Layouts for activities and fragments.                        |
| **ViewModel** | Business logic using ViewModel, use-cases, and entity models.    |


## ⚡ Tech Stack

* **Languages:** Kotlin + Java
* **UI:** XML with Material design guidelines
* **Architecture:** MVVM
* **Relational Database:** Room
* **Persistent Storage:** Data Store
* **Coroutines:** For async and reactive programming
* **Testing:** JUnit and Espresso
* **LeakCanary:** Memory leak monitoring tool at debug time
* **Firebase Crashlytics:** For Monitoring run time crashes

---

## 🧭 How to Run

1. Clone the repository:

   ```bash
   git clone https://github.com/<your-repo>/portfolio-holdings.git
   ```
2. Open in **Android Studio Ladybug (or newer)**.
3. Run the app on a device/emulator with internet access.

---

## 🧰 Requirements

* **Min SDK:** 24
* **Target SDK:** 34
* **Kotlin:** 2.x
* **Gradle:** 8.x
* **Android Studio:** Narwhal 3+

---

## 💡 Future Improvements

* Add alarm system using broadcast receiver to remine user for his/her Todo Task.
