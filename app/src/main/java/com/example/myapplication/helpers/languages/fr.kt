package com.example.myapplication.helpers.languages

import com.example.myapplication.LoginActivity
import com.example.myapplication.MainActivity
import com.example.myapplication.RegisterActivity
import com.example.myapplication.helpers.AllI18n
import com.example.myapplication.helpers.AppLanguage

val frI18n = AllI18n(
    langage = AppLanguage.FR,
    login = LoginActivity.I18n(
        activityTitle = "Connexion",
        loginButtonText = "Se connecter",
        emailPlaceholder = "Votre adresse mail",
        passwordPlaceholder = "Votre mot de passe (6 caractères minimum)",
        registerButtonText = "S'inscrire",
        registerHint = "Pas de compte ? Incrivez vous",
        authSuccess = "Authentification réussie",
        authFailed = "L'authentification a échoué"

    ),
    register = RegisterActivity.I18n(
        activityTitle = "S'inscrire",
        emailPlaceholder = "Votre adresse mail",
        passwordPlaceholder = "Votre mot de passe (6 caractères minimum)",
        passwordConfirmPlaceholder = "Confirmez votre mot de passe",
        emailEmptyError = "Veuillez renseigner votre adresse mail, le champ est vide",
        passwordEmptyError = "Votre mot de passe doit au moins contenir 6 caractères",
        passwordDontMatchError = "Vos mots de passes ne correspondent pas",
        authSuccess = "Authentification réussie",
        authFailed = "L'authentification a échoué",
        sigupButtonText = "S'inscrire"
    ),
    main = MainActivity.I18n(
        activityTitle = "Pics Manager",
        takePicture = "Nouvelle photo"
    )
)