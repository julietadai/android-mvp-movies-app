# android-mvp-movies-app
### This is a movies Android sample app in java. It shows basics of MVP architectural pattern.

# Architectural approach: Model-View-Presenter (MVP)

![gitDiagramCapture](https://user-images.githubusercontent.com/49270391/55721947-69589b00-5a0d-11e9-862a-342852128c82.PNG)

# Project layer structure

1. **Data layer**:  It contains POJOs and means to get Data from cloud or local storage.

2. **Domain layer**: It contains all business logic and interact between Data and Presentation layer - via interface and interactors. 

3. **Presentation layer**: It contains Activities, Fragments and views, which will only handle rendering views and will follow MVP pattern.

#### App that using the library libraries:
1. [Retrofit](https://github.com/square/retrofit)
2. [Gson](https://github.com/google/gson)
3. [Picasso](https://github.com/square/picasso)
4. [RoomDatabase](https://developer.android.com/topic/libraries/architecture/room)

#### Used data from:
- [themoviedb](https://www.themoviedb.org) - A database for movies and TV shows.


# Screenshot 
<p align="center">>
<img style="-webkit-user-select: none;cursor: zoom-in;" src="https://github.com/julietadai/android-mvp-movies-app/blob/master/movieTrailersDemo.gif" width="332" height="666">
</p>


### License
```
  MIT License

Copyright (c) 2018 Julie Dai

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
