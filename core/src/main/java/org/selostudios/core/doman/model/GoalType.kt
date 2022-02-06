package org.selostudios.core.doman.model

sealed class GoalType(val name: String) {
    object LoseWeight: GoalType("keep")
    object KeepWeight: GoalType("lose")
    object GainWeight: GoalType("gain")

    companion object {
        fun fromString(name: String) : GoalType {
            return when (name) {
                "keep" -> KeepWeight
                "lose" -> LoseWeight
                "gain" -> GainWeight
                else -> throw NullPointerException()
            }
        }
    }
}
