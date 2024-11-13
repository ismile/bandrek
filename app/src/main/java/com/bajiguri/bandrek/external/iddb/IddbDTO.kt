package com.bajiguri.bandrek.external.iddb

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class TokenResponse(
    @SerialName("access_token")
    val accessToken: String,
    @SerialName("expires_in")
    val expiresIn: Int,
    @SerialName("token_type")
    val tokenType: String,
    val retrievedAt: Long = System.currentTimeMillis()
)

@Serializable
data class Game(
    val id: Int,
    val artworks: List<Artwork>? = null,
    val category: Int,
    val cover: Cover,
    val firstReleaseDate: Long? = null,
    val genres: List<Genre>,
    val name: String,
    val platforms: List<Platform>,
    val rating: Double,
    val summary: String?
)

@Serializable
data class Artwork(
    val id: Int,
    val alphaChannel: Boolean? = null,
    val animated: Boolean,
    val game: Int,
    val height: Int,
    val imageId: String? = null,
    val url: String,
    val width: Int,
    val checksum: String
)

@Serializable
data class Cover(
    val id: Int,
    val alphaChannel: Boolean? = null,
    val animated: Boolean,
    val game: Int,
    val height: Int,
    val imageId: String? = null,
    val url: String,
    val width: Int,
    val checksum: String
)

@Serializable
data class Genre(
    val id: Int,
    val createdAt: Long? = null,
    val name: String,
    val slug: String,
    val updatedAt: Long? = null,
    val url: String,
    val checksum: String
)

@Serializable
data class Platform(
    val id: Int,
    val abbreviation: String,
    val alternativeName: String? = null,
    val category: Int,
    val createdAt: Long? = null,
    val name: String,
    val platformLogo: Int? = null,
    val slug: String,
    val updatedAt: Long? = null,
    val url: String,
    val versions: List<Int>,
    val websites: List<Int>,
    val checksum: String
)