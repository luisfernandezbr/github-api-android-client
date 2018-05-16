# GitHub Api Android Client

This project is a simple GitHub client whose main target is to test Android architecture solutions, provide samples for commom challenges that many projects faces, try to apply the best practices that are trending and test new architecture aproaches.


### Getting Started

Feel free to open issues criticizing the current solution and/or proposing new aproaches.


### Prerequisites

The current version was developed using Android Studio 3.0.1. 
Pay attention. You may need to updated some SDK components.


### Kotlin and Java

Currently the project is developed in most part in Koltin, but have some features in Java.


### Used libraries

* [Glide](https://bumptech.github.io/glide/)
* [Android Annotations](http://androidannotations.org/)
* [Retrofit](http://square.github.io/retrofit/)
* [RxJava](https://github.com/ReactiveX/RxJava)
* [RxAndroid](https://github.com/ReactiveX/RxAndroid)
* [CircleImageView](https://github.com/hdodenhof/CircleImageView)
* [Dagger 2](https://google.github.io/dagger/android)
* [MaterialSearchView](https://github.com/MiguelCatalan/MaterialSearchView)


### Helper solutions used

* [To enable Retrofit cache](https://gist.github.com/polbins/1c7f9303d2b7d169a3b1)
* [To call HTTPS on Android 16-21](https://github.com/square/okhttp/issues/2372)
* [To implement RecyclerView pagination solution](https://github.com/Suleiman19/Android-Pagination-with-RecyclerView)
* [To simplify Rx error handling](https://medium.com/mindorks/rxjava2-and-retrofit2-error-handling-on-a-single-place-8daf720d42d6)


### Mapped improvements

- To externalize pagination solution or to use a library that handle it
- Add unit and functional tests
- Adapt to run optimized on tablets
- Start to use Google Architecture components and Jetpack
- Please see the [Project Issues](https://github.com/luisfernandezbr/github-api-android-client/issues)


### License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE) file for details


### Acknowledgments

* Thanks [PurpleBooth](https://github.com/PurpleBooth) for the [Readme.md template](https://gist.github.com/PurpleBooth/109311bb0361f32d87a2)
