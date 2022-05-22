package com.jairbarzola.zoluxionescodechallenge.domain.usecase

import com.jairbarzola.zoluxionescodechallenge.domain.repository.LoginRepository
import com.jairbarzola.zoluxionescodechallenge.domain.util.Failure
import com.jairbarzola.zoluxionescodechallenge.domain.util.ResultType

class VerifyCredentialsUseCase(private val loginRepository: LoginRepository) {

    suspend operator fun invoke(user:String, password:String): ResultType<Unit,Failure> =
        loginRepository.verifyCredentials(user,password)
}