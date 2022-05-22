package com.jairbarzola.zoluxionescodechallenge.data.service

import com.jairbarzola.zoluxionescodechallenge.data.datasource.LoginDataSource
import com.jairbarzola.zoluxionescodechallenge.domain.util.Failure
import com.jairbarzola.zoluxionescodechallenge.domain.util.ResultType
import kotlinx.coroutines.delay

class LoginDataSourceImpl: LoginDataSource {

    val USER = "Admin"
    val PASSWORD = "Password*123"

    override suspend fun verifyCredentials(
        user: String,
        password: String
    ): ResultType<Unit, Failure> {
        delay(3000L)
        return if (user == USER && password == PASSWORD) {
            ResultType.Success(Unit)
        } else {
            ResultType.Error(Failure("Credenciales incorrectas"))
        }
    }
}