# Nook
Nook is a Kotlin app for users to organize priorities, checklists, and notes. Users have the ability to add, modify, or delete any of these items. 
Additionally, users can bump the priority items based on priority level of low, medium or high. 

The purpose of this app is to showcase my understanding of Room library, dependency injection, and Epoxy RecyclerView. 

Minimum SDK Version: 26
Compile SDK Version: 33

## Architecture
The architecture of this project is MVVM (Model View ViewModel) Clean Architecture. The app is built
in a way for easy readability by other developers. I have followed the [recommended app architecture](https://developer.android.com/topic/architecture#recommended-app-arch)
from Android.

The architecture is divided into three layers:
* UI Layer (Presentation Layer)
* Data Layer (Data Source & Repository)

### UI Layer
The responsibility of the UI layer (or presentation layer) is to display the application data on the screen.
In this project, the UI layer includes UI elements from [AirBnB Epoxy RecyclerView](https://airbnb.io/projects/epoxy/) library.
All the UI element designing is done using XML.

<p align="center" width="100%">
    <img width="75%" src="https://user-images.githubusercontent.com/9715067/197088633-488dbb42-a099-42e9-a788-bcfe5ba64eef.png" alt="Unidirectional Data Flow"/>
</p>

### Data Layer
The [data layer](https://developer.android.com/topic/architecture/data-layer) contains business logic and repositories containing
data sources.
I am using [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) dependency injection library to provide all the dependencies I have to pass into the data layer.


## In this branch, you'll find:

* [AirBnB Epoxy RecyclerViews](https://github.com/airbnb/epoxy) - Epoxy is an Android library for building complex screens in a RecyclerView.
* [Modern App Architecture](https://developer.android.com/topic/architecture)
* [Retrofit](https://square.github.io/retrofit/) - Interacts with the API and send network requests with OkHttp.
* [Room](https://developer.android.com/training/data-storage/room) - Create, store, and manage persistent data backed by an SQLite database.
* [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) - Dependency injection plays a central role in the architectural pattern used.
* [Kotlinx Coroutines](https://github.com/Kotlin/kotlinx.coroutines) - Library Support for coroutines. I used this for asynchronous programming to obtain data from the network.
* [Flows](https://developer.android.com/kotlin/flow) - A flow is conceptually a stream of data that can be computed asynchronously. The emitted values must be of the same type.

## Screenshots:
<p align="center" width="100%">
    <img width="40%" src="https://tylerryden.com/images/nook-home2.png" alt="Nook home screen"/>
    <img width="40%" src="https://tylerryden.com/images/nook-add-priority.png" alt="Nook add priority screen"/>
    <img width="40%" src="https://tylerryden.com/images/nook-notes.png" alt="Nook notes screen"/>
    <img width="40%" src="https://tylerryden.com/images/nook-priorities.png" alt="Nook priorities screen"/>
</p>