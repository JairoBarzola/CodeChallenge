package com.jairbarzola.zoluxionescodechallenge.data.datasource

import com.jairbarzola.zoluxionescodechallenge.domain.util.Failure
import com.jairbarzola.zoluxionescodechallenge.domain.util.ResultType

interface LoginDataSource {
    suspend fun verifyCredentials(user:String, password:String): ResultType<Unit,Failure>
}