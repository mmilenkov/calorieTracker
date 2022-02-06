package org.selostudios.core.doman.usecase

class FilterOutDigits {

    operator fun invoke(text: String): String {
        return text.filter { it.isDigit() }
    }
}