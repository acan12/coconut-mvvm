# coconut-mvvm
Coconut framework in MVVM version for Android development

Release Notes:
- add lost connection handler in base



[![](https://jitpack.io/v/acan12/coconut.svg)](https://jitpack.io/#acan12/coconut)

**Recomended Directory structure**
 - `Project directory`


     > font directory path: `src/main/res/font/...`

     ```aidl

     < YOUR_NAMESPACE_APPLICATION >
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
        |       |--- dao
        |       |--- repository
        |
        |
        |--- viewmodel
        |      |
        |      |-- {name}ViewModel.java
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
        implementation 'com.github.acan12:coconut-mvvm:1.0.0-alpha4'
		// HILT
        implementation "com.google.dagger:hilt-android:$hilt_version"
        kapt "com.google.dagger:hilt-compiler:$hilt_version"
		...
	}
```

**3. Add Application project class extend BaseApp**
```java
    public class App extends BaseApp {
        private static Context context;

        @Override
        public void onCreate() {
            super.onCreate();
            context = getApplicationContext();
            setupBuilder(DaggerAppComponent.builder(), this);
        }

        public static AppComponent getAppComponent() {
            if(context == null) return null;
            return getComponent();
        }

        ...
    }
```

**4. Add Application class into `AndroidManifest.java`**
```java
   ...
   <application
           android:name=".<AppClassName>"
           android:allowBackup="true"
           android:icon="@mipmap/ic_launcher"
           android:label="@string/app_name"
           android:supportsRtl="true"
           android:theme="@style/AppTheme">
  ...

```

**5. Integrate Base Code with Coconut Framework**
 - `BaseApi`

    ```java
        public class Api extends BaseApi {

            synchronized private static ApiService initApiDomain() {
                getInstance().setApiDomain(IConfig.API_BASE_URL);
                return (ApiService) getInstance().setupApi(App.getAppComponent(), ApiService.class);
                //return setupApi(App.getAppComponent(), ApiService.class, true); -- if set allow untrusted SSL calling
            }

            synchronized public static void doApiSources(Callback callback) {
                initApiDomain().callApiSources("en").enqueue((Callback<SourceResponse>) callback);
            }

    ```
 - `BaseActivity`
    ```java
       // ... interface ICustomView extends IView

       public class MainActivity extends BaseActivity implements ICustomView {
           ...
       }
    ```
 - `BaseFragment` ,_if use fragment_
    ```java
       // ... interface ICustomView extends IView

       public class MainFragment extends BaseFragment implements ICustomView {
           ...
       }
    ```

- `BaseUseCase`
  ```java
       // ... bridge among repository and viewmodel

       public class CustomViewModel implements ViewModel{
           ...
       }
    ```

- `BaseDialog`
    ```java
       public class MainDialog extends BaseDialog {
          ...

          @Override
          protected void onCreate(Bundle savedInstanceState) {
              ...
          }
       }


    ```

 - `BaseDao / Interactor`
    ```java
        public class ResourceDao extends BaseDao {
            public ResourceDao(BaseActivity ac) {
                super(ac);
            }

            public void getSourcesDAO(BaseActivity ac, Callback callback) {
                Api.doApiSources(ac, callback);
            }
            ...

            private Database db;

            public void saveUser(BaseActivity ac){
                db = Database.initDatabase(context); -- setup database configuration
                db.saveToRealm()
            }
    ```

 - `BaseResponse`
    ```java
       public class ArticleResponse extends BaseResponse {
           ...
       }
    ```


**6. Implementation loading message within Rx**

- `(Activity / Fragment)`
```aidl
    -> ((ResourcePresenter) BasePresenter.getInstance(this, ResourcePresenter.class)).getSourceRX("Ambil Data");
```

- `(Interface UI class)`
```aidl
    -> create self interface extends IView as parent of interface in framework
```

- `(Presenter)`
```aidl
    -> @Override
        public void getSourceRX(String messageLoading) {
            new ResourceDao(this).getSourceRXDAO()
                    .subscribe(new RxObserver<ProfileResponseModel>(iv, messageLoading) {
                        @Override
                        public void onNext(Object o) {
                            super.onNext(o);
                            [Interface UI class].handle[your function name]((SourceResponse) o);
                        }
                    });
        }
```

- `(Interface DAO/ Interactor)`
```aidl
    -> create self interface extends IDao as parent of interface in framework

    [sample code] ->

        private [custom presenter name]Presenter.OnPresenterResponseCallback onPresenterResponseCallback;
        private IResourceDao rdao;

        // definition usecase
        public interface I[custom presenter name]Dao extends IDaoPresenter {

            ...
            void getSourceRX(String messageLoading);

            ...


        }

        public [custom presenter name]Dao(I[custom presenter name] rdao) {
            this.rdao = rdao;
        }

        public [custom presenter name]Dao(I[custom presenter name]Dao rdao, ResourcePresenter.OnPresenterResponseCallback onPresenterResponseCallback) {
            this.rdao = rdao;
            this.onPresenterResponseCallback = onPresenterResponseCallback;
        }
        ...

```

Change Log Version:
- `[1.0.0-alpha4]` :
    * support sharePreference security provided by androidX
    * Convertor Json to String and String to Json
    * support with dagger hilt dependency injection
    * handle page for not have internet connection case
    * support MVVM android architecture with UseCase
    * support Caroutine and Rx for async process