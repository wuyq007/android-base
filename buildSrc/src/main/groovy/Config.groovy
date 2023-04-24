class Config {

    public static appName = 'AndroidBase'
    public static applicationId = 'com.example.android.base'

    public static compileSdk = 30
    public static minSdk = 24
    public static targetSdk = 30
    public static versionCode = 1
    public static versionName = "1.0"


    /**
     * 各个模块名称
     */
    public static modules = [
            libs  : [':libs:base-lib', ':libs:http-lib'],
            module: [],
            common: [],
    ]

    /**
     * 组件版本
     */
    private static versions = [
            paging_version: '3.1.1',
            nav_version   : '2.5.3',
            room_version  : '2.5.0',
    ]

    /**
     * 基础库
     */
    public static packages = [
            base           : [
//                    //targetVersion = 33
//                    'androidx.core:core-ktx:1.9.0',
//                    'androidx.appcompat:appcompat:1.6.1',
//                    'com.google.android.material:material:1.8.0',
//
//                    //targetVersion = 32
//                    'androidx.core:core-ktx:1.8.0',
//                    'androidx.appcompat:appcompat:1.5.1',
//                    'com.google.android.material:material:1.8.0',
//
//                    //targetVersion = 31
//                    'androidx.core:core-ktx:1.8.0',
//                    'androidx.appcompat:appcompat:1.4.2',
//                    'com.google.android.material:material:1.6.1',

                    //targetVersion = 30
                    'androidx.core:core-ktx:1.6.0',
                    'androidx.appcompat:appcompat:1.3.1',
                    'com.google.android.material:material:1.4.0',

                    'androidx.multidex:multidex:2.0.1',
                    'androidx.recyclerview:recyclerview:1.2.1',
                    'androidx.constraintlayout:constraintlayout:2.1.4',
                    'androidx.swiperefreshlayout:swiperefreshlayout:1.1.0',

                    'com.google.code.gson:gson:2.9.0',

                    'org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4',
                    'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4',

                    'androidx.collection:collection:1.2.0',
                    'androidx.collection:collection-ktx:1.2.0',
            ],
            baseTest       : [
                    'junit:junit:4.13.2',
            ],
            baseAndroidTest: [
                    'androidx.test.ext:junit:1.1.5',
                    'androidx.test.espresso:espresso-core:3.4.0'
            ],
    ]


    public static jetpack = [
            datastore : [
                    'androidx.datastore:datastore-preferences:1.0.0',
                    'androidx.datastore:datastore-preferences-core:1.0.0'
            ],
            navigation: [
//                    "androidx.navigation:navigation-fragment:${versions.nav_version}",
                    "androidx.navigation:navigation-fragment-ktx:${versions.nav_version}",
//                    "androidx.navigation:navigation-ui:${versions.nav_version}",
                    "androidx.navigation:navigation-ui-ktx:${versions.nav_version}",
            ],

            paging    : "androidx.paging:paging-runtime:${versions.paging_version}",
            pagingTest: "androidx.paging:paging-common:${versions.paging_version}",

            room      : ["androidx.room:room-runtime:${versions.room_version}",
                         "androidx.room:room-ktx:${versions.room_version}",],
            roomKapt  : "androidx.room:room-compiler:${versions.room_version}",
            roomTest  : "androidx.room:room-testing:${versions.room_version}",
    ]

    public static square = [
            okhttp : [
                    'com.squareup.okhttp3:okhttp:4.10.0',
                    'com.squareup.okhttp3:logging-interceptor:4.10.0',
            ],
            retrofit      : [
                    'com.squareup.retrofit2:retrofit:2.9.0',
                    'com.squareup.retrofit2:converter-gson:2.9.0',
                    'com.squareup.retrofit2:converter-scalars:2.9.0'
            ],
            okio    : 'com.squareup.okio:okio:3.0.0',
    ]


    public static commonView = [
            background    : 'com.github.JavaNoober.BackgroundLibrary:libraryx:1.7.6'
    ]



}