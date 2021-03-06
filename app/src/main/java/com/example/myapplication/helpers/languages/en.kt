package com.example.myapplication.helpers.languages

import com.example.myapplication.LoginActivity
import com.example.myapplication.MainActivity
import com.example.myapplication.RegisterActivity
import com.example.myapplication.SavePhotoActivity
import com.example.myapplication.helpers.AllI18n
import com.example.myapplication.helpers.AppLanguage

val enI18n = AllI18n(
    langage = AppLanguage.EN,
    login = LoginActivity.I18n(
        activityTitle = "Connexion",
        loginButtonText = "Login",
        emailPlaceholder = "Your email adress",
        passwordPlaceholder = "Your password (min. 6 chars)",
        registerButtonText = "Register",
        registerHint = "No account ? Register",
        authSuccess = "Authentification successful",
        authFailed = "Authentification failed"
    ),
    register = RegisterActivity.I18n(
        activityTitle = "Sign up",
        emailPlaceholder = "Your email adress",
        passwordPlaceholder = "Your password (min? 6 chars)",
        passwordConfirmPlaceholder = "Confirm your password",
        emailEmptyError = "The email field is empty",
        passwordEmptyError = "The password field is empty (min. 6 chars)",
        passwordDontMatchError = "The passwords doesn't match",
        authSuccess = "Authentification successful",
        authFailed = "Authentification failed",
        sigupButtonText = "Sign up"
    ),
    main = MainActivity.I18n(
        activityTitle = "Pics Manager",
        takePicture = "Take a new picture"
    ),
    savePhoto = SavePhotoActivity.I18n(
        activityTitle = "Save photo",
        pictureName = "Name your picture",
        album = "Album",
        save = "Save",
        discard = "Discard",
        errorAlbum = "Please select an album",
        errorNameEmpty = "Please enter a name",
        errorNameAlreadyTaken = "Name already taken"
    )
)