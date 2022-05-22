package com.jairbarzola.zoluxionescodechallenge.data.remote

import com.jairbarzola.zoluxionescodechallenge.data.remote.URL.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

class ServiceFactory {
    companion object {
        private const val API_BASE_URL = BASE_URL
        private val httpClient = OkHttpClient()

        @JvmStatic
        fun <S> createService(serviceClass: Class<S>): S {
            val builder = Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .sslSocketFactory(getTrustAllHostsSSLSocketFactory())
                .build()


            val retrofit = builder
                .client(httpClient).client(client).build()
            return retrofit.create(serviceClass)
        }

        private fun getTrustAllHostsSSLSocketFactory(): SSLSocketFactory? {
            try {
                val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {

                    override fun getAcceptedIssuers(): Array<X509Certificate> {
                        return arrayOf()
                    }

                    @Throws(CertificateException::class)
                    override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
                    }

                    @Throws(CertificateException::class)
                    override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
                    }
                })
                val sslContext = SSLContext.getInstance("SSL")
                sslContext.init(null, trustAllCerts, java.security.SecureRandom())
                return sslContext.socketFactory
            } catch (e: KeyManagementException) {
                e.printStackTrace()
                return null
            } catch (e: NoSuchAlgorithmException) {
                e.printStackTrace()
                return null
            }

        }
    }
}