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
    val id: Int = 0,
    val artworks: List<Artwork>? = null,
    val category: Int = 0,
    val cover: Cover = Cover(),
    val firstReleaseDate: Long? = null,
    val genres: List<Genre> = emptyList(),
    val name: String = "",
    val platforms: List<Platform> = emptyList(),
    val rating: Double = 0.0,
    val summary: String? = null
)

@Serializable
data class Artwork(
    val id: Int = 0,
    val alphaChannel: Boolean? = null,
    val animated: Boolean = false,
    val game: Int = 0,
    val height: Int = 0,
    val imageId: String? = null,
    val url: String = "",
    val width: Int = 0,
    val checksum: String = ""
)

@Serializable
data class Cover(
    val id: Int = 0,
    val alphaChannel: Boolean? = null,
    val animated: Boolean = false,
    val game: Int = 0,
    val height: Int = 0,
    val imageId: String? = null,
    val url: String = "",
    val width: Int = 0,
    val checksum: String = ""
)

@Serializable
data class Genre(
    val id: Int = 0,
    val createdAt: Long? = null,
    val name: String = "",
    val slug: String = "",
    val updatedAt: Long? = null,
    val url: String = "",
    val checksum: String = ""
)

@Serializable
data class Platform(
    val id: Int = 0,
    val abbreviation: String = "",
    val alternativeName: String? = null,
    val category: Int = 0,
    val createdAt: Long? = null,
    val name: String = "",
    val platformLogo: Int? = null,
    val slug: String = "",
    val updatedAt: Long? = null,
    val url: String = "",
    val versions: List<Int> = emptyList(),
    val websites: List<Int> = emptyList(),
    val checksum: String = ""
)