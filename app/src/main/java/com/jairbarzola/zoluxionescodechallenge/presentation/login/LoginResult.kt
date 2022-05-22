package com.jairbarzola.zoluxionescodechallenge.presentation.login

sealed class LoginResult {

    object ShowLoading : LoginResult()
    object GoToHome : LoginResult()
    data class ShowUserError(val error: String?) :LoginResult()
    data class ShowPasswordError(val error: String?) :LoginResult()
    data class ShowErrorGeneral(val error: String) :LoginResult()

}