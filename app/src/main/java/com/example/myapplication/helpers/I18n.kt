package com.example.myapplication.helpers

import android.app.Activity
import com.example.myapplication.LoginActivity
import com.example.myapplication.MainActivity
import com.example.myapplication.RegisterActivity
import com.example.myapplication.helpers.languages.enI18n
import com.example.myapplication.helpers.languages.frI18n
import java.util.*

/**
 * This is a class for app Translation
 * This class is referencing all view translations for the application
 * */
class AllI18n(
    val langage : AppLanguage,
    val login : LoginActivity.I18n,
    val register: RegisterActivity.I18n,
    val main : MainActivity.I18n
)

/**
 * This val is used to store all translations for the application
 * */
val allI18n : AllI18n = when(Locale.getDefault().toString()) {
    AppLanguage.FR.javaLocales -> frI18n
    AppLanguage.EN.javaLocales -> enI18n
    else -> enI18n // default language
}

enum class AppLanguage(val iso639_1 : String, val iso639_2 : String, val javaLocales : String)  {
    FR("fr", "fra", "fr_FR"),
    EN("en", "eng", "en_US")
}

interface MetaI18n {
    val activityTitle : String
}