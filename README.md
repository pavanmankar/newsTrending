# MVVM Architecture Android: Sample App
This repository contains a sample app that implements MVVM architecture using Kotlin, Dagger,
Retrofit, Coroutines, Flow, StateFlow, etc.

## Major Highlights

- MVVM Architecture
- Kotlin
- Dagger2
- Retrofit
- Coroutines
- Flows
- Stateflow
- View binding
- Unit Test

#### The app contains the following packages:
1. **data**: It contains all the data accessing and manipulating components.
2. **di**: Dependency providing classes using Dagger2.
3. **ui**: View classes along with their corresponding ViewModel.
4. **utils**: Utility classes.

# Application Architecture
![alt text](https://cdn-images-1.medium.com/max/1600/1*OqeNRtyjgWZzeUifrQT-NA.png)
![alt text](https://media.geeksforgeeks.org/wp-content/uploads/20210720231513/viewmodal.png)

The main advantage of using MVVM, there is no two-way dependency between ViewModel and Model, unlike MVP. Here the view can observe the data changes in the ViewModel as we are using LiveData which is lifecycle aware. The viewmodel-to-view communication is achieved through observer pattern (basically observing the state changes of the data in the viewmodel).


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

### License

```
   Copyright (C) 2022

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
```
