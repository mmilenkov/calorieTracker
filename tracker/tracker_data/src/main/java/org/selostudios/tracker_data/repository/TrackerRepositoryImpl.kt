package org.selostudios.tracker_data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.selostudios.tracker_data.local.TrackerDAO
import org.selostudios.tracker_data.mapper.toFoodEntity
import org.selostudios.tracker_data.mapper.toTrackableFood
import org.selostudios.tracker_data.mapper.toTrackedFood
import org.selostudios.tracker_data.remote.OpenFoodAPI
import org.selostudios.tracker_data.remote.dto.Product
import org.selostudios.tracker_domain.model.TrackableFood
import org.selostudios.tracker_domain.model.TrackedFood
import org.selostudios.tracker_domain.repository.TrackerRepository
import java.lang.Exception
import java.time.LocalDate

class TrackerRepositoryImpl(
    private val dao: TrackerDAO,
    private val api: OpenFoodAPI
): TrackerRepository {
    override suspend fun searchForFood(
        query: String,
        page: Int,
        pageSize: Int
    ): Result<List<TrackableFood>> {
        return try {
            val searchDTO = api.searchFood(
                query = query,
                page = page,
                pageSize = pageSize
            )
            Result.success(
                searchDTO
                    .products
                    .filter { calculateNutrimentsWithinBounds(it) }
                    .mapNotNull { it.toTrackableFood() }
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    private fun calculateNutrimentsWithinBounds(product: Product): Boolean {
        val calculatedCalories =
            product.nutriments.carbohydrates100g * 4f
        +product.nutriments.fat100g * 9f
        +product.nutriments.proteins100g * 4f
        val upperBound = calculatedCalories * 1.02f
        val lowerBound = calculatedCalories * 0.98f

        return product.nutriments.energyKcal100g in (lowerBound..upperBound)
    }

    override suspend fun insertFood(food: TrackedFood) {
        dao.insertTrackedFood(food.toFoodEntity())
    }

    override suspend fun deleteFood(food: TrackedFood) {
        dao.deleteTrackedFood(food.toFoodEntity())
    }

    override fun getFoodsForDate(localDate: LocalDate): Flow<List<TrackedFood>> {
        return dao.getFoodsForDate(
            localDate.dayOfMonth,
            localDate.monthValue,
            localDate.year
        ).map { entities ->
            entities.map {
                it.toTrackedFood()
            }
        }
    }
}