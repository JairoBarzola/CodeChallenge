package com.jairbarzola.zoluxionescodechallenge.data.repository

import com.jairbarzola.zoluxionescodechallenge.data.datasource.LoginDataSource
import com.jairbarzola.zoluxionescodechallenge.domain.repository.LoginRepository
import com.jairbarzola.zoluxionescodechallenge.domain.util.Failure
import com.jairbarzola.zoluxionescodechallenge.domain.util.ResultType

class LoginRepositoryImpl(private val loginDataSource: LoginDataSource) : LoginRepository {
    override suspend fun verifyCredentials(user: String, password: String): ResultType<Unit, Failure>
    = loginDataSource.verifyCredentials(user,password)
}