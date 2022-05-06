# coconut-mvvm
Coconut framework in MVVM version for Android development

[![](https://jitpack.io/v/acan12/coconut-mvvm.svg)](https://jitpack.io/#acan12/coconut-mvvm)

Change Log Version:
- `[1.0.0-alpha4]` :
  * support already with MVVM android architecture
  * support sharePreference security provided by androidX
  * Convertor Json to String and String to Json
  * support with dagger hilt dependency injection
  * handle page for not have internet connection case
  * support Caroutine and Rx for async process
  
**Recomended Directory structure**
- `Project directory`


     > font directory path: `src/main/res/font/...`

     ```aidl

     << yourNamespaceApplication >>
        |
        |-- App.java
        |-- IConfig.java
        |--- di
            |--- module
        |--- domain
            |--- usecases
        |
        |--- model
        |       |
        |       |-- api
        |       |    |
        |       |    |-- request
        |       |    |-- response
        |       |    |-- Api.java
        |       |    |-- ApiService.java
        |       |
        |       |--- pojo
        |       |--- dao (model database)
        |       |--- repository
        |
        |
        |--- viewmodel
        |      |
        |      |-- << name >>ViewModel.java
        |
        |--- ui
             |
             |-- activity
             |-- fragment
             |-- interfaces
             |-- adapter




     ```


## Installation guide :

**1. Add the JitPack repository to your build file**
```allprojects
{
    repositories {
      ...
   	  maven { url 'https://jitpack.io' }
    }
}
```

**2. Add the dependency**
```
    dependencies {
		// Coconut MVVM
        implementation "com.github.acan12:coconut-mvvm:$coconut_mvvm_version"
		// HILT
        implementation "com.google.dagger:hilt-android:$hilt_version"
        kapt "com.google.dagger:hilt-compiler:$hilt_version"
		...
	}
```

**3. Add Application project class extend BaseApp**
```kotlin
    @HiltAndroidApp
    class App : BaseApp() {
```

**4. Add Application class into `AndroidManifest.java`**
```java
   ...
   <application
        android:allowBackup="true"
        android:name=".<< yourApplication >>"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/Theme.sample" >
  ...

```

**5. Integrate Base Code with Coconut Framework**
- `Api`

   ```kotlin
       open class Api 
       @Inject 
       constructor(val apiService: IApiService) : BaseApi() 

   ```
- `Activity`
   ```kotlin
       @AndroidEntryPoint
       class MainActivity : BaseActivity(), IView
   ```
- `Fragment`
   ```kotlin
       class DemoFragment : BaseFragment() 
      
   ```

- `ViewModel`
    ```kotlin
        @HiltViewModel
        class MainViewModel @Inject constructor(
            private val repository: SampleLocationRepository,
            private val locationUseCases: SampleLocationUseCases
        ) : BaseViewModel() {
       
    ```

- `UseCase`
  ```kotlin
       class SampleLocationUseCases @Inject constructor(
            private val locationRepository: SampleLocationRepository
        ) : BaseUseCase<Resource<SampleLocationResponse>>() {
        
        override suspend fun performAction()
            : Flow<Resource<SampleLocationResponse>> {
            return locationRepository.getLocationCaroutine() 
        }
    ```

- `Dialog`
    ```kotlin
       class SampleDialog(val iview: IView) :
            BaseDialog(iview as Activity, R.style.CoconutDialogFullScreen)


    ```

- `DAO (Data Access Object) for database`
   ```kotlin
       @Dao
       interface LocationDao {
       
           @Query("SELECT * FROM location ORDER BY id DESC")
           fun getLocations(): List<LocationEntity>
       
           @Insert(onConflict = OnConflictStrategy.REPLACE)
           suspend fun insertLocation(location: LocationEntity)
       }
   ```

- `Response`
   ```kotlin
       @JsonIgnoreProperties(ignoreUnknown = true)
       class SampleLocationResponse() : BaseResponse() {}
   ```

**5. No Internet Connection in App**

        [Activity]
        class MainActivity : BaseActivity(), IView {
            
            viewModelLive.location.observe(this) { resource ->
                when(resource) {
                    is Resource.Error -> {
                        when (resource.throwable) {
                            is NetworkLostConnectionException -> {
                                handleNoConnectionInternet()
                            }
                        }
                    }
                    ...
                }
                ...
            }
            ...
            
            override fun handleNoConnectionInternet() {
                Toast.makeText(this, "No Connection", Toast.LENGTH_LONG).show()
            }
            ...
        }
        
    ```