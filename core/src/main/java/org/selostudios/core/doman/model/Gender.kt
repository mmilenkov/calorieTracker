package org.selostudios.core.doman.model

sealed class Gender(val name: String) {
    object Male: Gender("male")
    object Female: Gender("female")

    companion object {
        fun fromString(name: String) : Gender {
            return when (name.lowercase()) {
                "male" -> Male
                "female" -> Female
                else -> Male //default fallback
            }
        }
    }
}
