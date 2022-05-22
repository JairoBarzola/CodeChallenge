package com.jairbarzola.zoluxionescodechallenge.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jairbarzola.zoluxionescodechallenge.domain.usecase.VerifyCredentialsUseCase
import com.jairbarzola.zoluxionescodechallenge.domain.util.ResultType
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class LoginViewModel(private val verifyCredentialsUseCase: VerifyCredentialsUseCase) : ViewModel() {

    private val _viewStateMutable = MutableLiveData<LoginResult>()
    val viewStateMutable: LiveData<LoginResult> = _viewStateMutable


    fun verifyCredentials(user:String, password: String){
        when {
            user.trim().isEmpty() -> {
                _viewStateMutable.value = LoginResult.ShowUserError("Ingresa un usuario")
                return
            }
            user.length < 2 -> {
                _viewStateMutable.value = LoginResult.ShowUserError("Ingresa un usuario correcto")
                return
            }
            else -> {
                _viewStateMutable.value = LoginResult.ShowUserError(null)
            }
        }
        if(password.trim().isEmpty()) {
            _viewStateMutable.value = LoginResult.ShowPasswordError("Ingresa la contraseña")
            return
        } else {
            _viewStateMutable.value = LoginResult.ShowPasswordError(null)
        }

        viewModelScope.launch(IO) {
            _viewStateMutable.postValue(LoginResult.ShowLoading)
            when (val result = verifyCredentialsUseCase.invoke(user, password)) {
                is ResultType.Success->{
                    _viewStateMutable.postValue(LoginResult.GoToHome)
                }
                is ResultType.Error->{
                    _viewStateMutable.postValue(LoginResult.ShowErrorGeneral(result.value.message.orEmpty()))
                }
            }
        }

    }

}