package com.bajiguri.bandrek.external.iddb

import com.bajiguri.bandrek.AppDao
import com.bajiguri.bandrek.BuildConfig
import com.bajiguri.bandrek.Setting
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.*
import io.ktor.client.request.forms.submitForm
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpHeaders
import io.ktor.http.parameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

val IDDB_TOKEN = "IDDB_TOKEN"
val IDDB_EXPIRES_AT = "EXPIRES_AT"
val IDDB_CLIENT_ID = BuildConfig.IDDB_CLIENT_ID
val IDDB_CLIENT_SECRET = BuildConfig.IDDB_CLIENT_SECRET

class IddbClient @Inject constructor(
    private val httpClient: HttpClient,
    private val appDao: AppDao
) {
    suspend fun getToken(): TokenResponse {
        val response: TokenResponse =
            httpClient.submitForm("https://id.twitch.tv/oauth2/token", formParameters = parameters {
                append("client_id", IDDB_CLIENT_ID)
                append("client_secret", IDDB_CLIENT_SECRET)
                append("grant_type", "client_credentials")
            }).body()

        appDao.upsertSetting(Setting(IDDB_TOKEN, response.accessToken))
        appDao.upsertSetting(
            Setting(
                IDDB_EXPIRES_AT,
                response.retrievedAt.plus(response.expiresIn).toString()
            )
        )
        return response
    }

    suspend fun token(): String {
        val token = appDao.getSettingValueByKey(IDDB_TOKEN)
        val expiresAt = appDao.getSettingValueByKey(IDDB_EXPIRES_AT)
        return if (token == null || expiresAt == null) {
            getToken().accessToken
        } else {
            if (expiresAt.toLong() < System.currentTimeMillis()) {
                getToken().accessToken
            } else {
                token
            }
        }
    }

    suspend fun searchRomByName(name: String, platformCode: String): List<Game> {
        val token = token()
        val code = if (platformCode == "psx") "ps" else platformCode
        val response: List<Game> = httpClient.post("https://api.igdb.com/v4/games") {
            headers {
                append(HttpHeaders.Accept, "application/json")
                append(HttpHeaders.Authorization, "Bearer $token")
                append("Client-ID", IDDB_CLIENT_ID)
                append(HttpHeaders.ContentType, "text/plain")
            }
            setBody("search \"$name\"; fields name,artworks.*,category,cover.*,genres.*,rating,summary,first_release_date,platforms.*;where platforms.slug = \"$code\";limit 5;")
        }.body()

        return response
    }
}