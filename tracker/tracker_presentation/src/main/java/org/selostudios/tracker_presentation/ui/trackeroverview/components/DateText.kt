package org.selostudios.tracker_presentation.ui.trackeroverview.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import org.selostudios.core.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter

//Created as a composable to access string resources
@Composable
fun parseDateToText(date: LocalDate): String {
    val today = LocalDate.now()
    return when (date) {
        today -> stringResource(id = R.string.today)
        today.minusDays(1) -> stringResource(id = R.string.yesterday)
        today.plusDays(1) -> stringResource(id = R.string.tomorrow)
        else -> DateTimeFormatter.ofPattern("dd LLLL").format(date)
    }
}