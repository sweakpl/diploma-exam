package com.sweak.diplomaexam.common

// Retrofit base URL
const val BASE_URL = "https://diploma-examination-system.herokuapp.com/"

// Dummy state variables - USED ONLY TO SIMULATE APP BEHAVIOR!
lateinit var DUMMY_GLOBAL_USER_ROLE: UserRole
lateinit var DUMMY_GLOBAL_USER_EMAIL: String
lateinit var DUMMY_GLOBAL_OTHER_USER_ROLE: UserRole
lateinit var DUMMY_GLOBAL_OTHER_USER_EMAIL: String
var DUMMY_GLOBAL_HAS_SESSION_BEEN_STARTED: Boolean = false