package org.selostudios.tracker_presentation.ui.trackeroverview.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import org.selostudios.core_ui.LocalSpacing
import org.selostudios.tracker_presentation.ui.trackeroverview.Meal
import org.selostudios.core.R
import org.selostudios.tracker_presentation.ui.shared.UnitDisplay

@Composable
fun ExpandableMeal(
    meal: Meal,
    onToggleMealClick: () -> Unit,
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    val spacing = LocalSpacing.current
    val context = LocalContext.current
    
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
            .fillMaxWidth()
            .clickable { onToggleMealClick() }
            .padding(spacing.medium),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(painter = painterResource(id = meal.drawableRes),
                contentDescription = meal.name.asString(context = context)
            )
            Spacer(modifier = Modifier.width(spacing.medium))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = meal.name.asString(context = context),
                        style = MaterialTheme.typography.h3
                    )
                    Icon(imageVector = if (meal.isExpanded) {
                        Icons.Default.KeyboardArrowUp
                    } else {
                        Icons.Default.KeyboardArrowDown
                    },
                        contentDescription = if (meal.isExpanded) {
                            stringResource(id = R.string.collapse)
                        } else {
                            stringResource(id = R.string.extend)
                        }
                    )
                }
                Spacer(modifier = Modifier.height(spacing.small))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    UnitDisplay(
                        amount = meal.calories,
                        unit = stringResource(id = R.string.kcal),
                        amountTextSize = 24.sp
                    )
                    Row {
                        NutrientInfo(
                            name = stringResource(id = R.string.carbs),
                            amount = meal.carbs,
                            amountTextSize = 16.sp,
                            unit = stringResource(id = R.string.grams),
                            unitTextSize = 12.sp
                        )
                        Spacer(modifier = Modifier.width(spacing.small))
                        NutrientInfo(
                            name = stringResource(id = R.string.protein),
                            amount = meal.protein,
                            amountTextSize = 16.sp,
                            unit = stringResource(id = R.string.grams),
                            unitTextSize = 12.sp
                        )
                        Spacer(modifier = Modifier.width(spacing.small))
                        NutrientInfo(
                            name = stringResource(id = R.string.fat),
                            amount = meal.fat,
                            amountTextSize = 16.sp,
                            unit = stringResource(id = R.string.grams),
                            unitTextSize = 12.sp
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(spacing.medium))
        AnimatedVisibility(visible = meal.isExpanded) {
            content()
        }
    }
}