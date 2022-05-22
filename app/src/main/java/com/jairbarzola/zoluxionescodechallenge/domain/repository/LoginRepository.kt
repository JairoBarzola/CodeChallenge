package com.jairbarzola.zoluxionescodechallenge.domain.repository

import com.jairbarzola.zoluxionescodechallenge.domain.util.Failure
import com.jairbarzola.zoluxionescodechallenge.domain.util.ResultType

interface LoginRepository {
    suspend fun verifyCredentials(user:String, password:String) : ResultType<Unit, Failure>
}