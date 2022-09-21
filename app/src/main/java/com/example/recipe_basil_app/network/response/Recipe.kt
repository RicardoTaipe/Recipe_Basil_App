package com.example.recipe_basil_app.network.response

import com.example.recipe_basil_app.network.NetworkConstants.IMAGE_BASE_URL
import com.squareup.moshi.Json

data class Recipe(
    @Json(name = "dateModified")
    val dateModified: String? = null,
    @Json(name = "idMeal")
    val idMeal: String? = null,
    @Json(name = "strArea")
    val strArea: String? = null,
    @Json(name = "strCategory")
    val strCategory: String? = null,
    @Json(name = "strCreativeCommonsConfirmed")
    val strCreativeCommonsConfirmed: String? = null,
    @Json(name = "strDrinkAlternate")
    val strDrinkAlternate: String? = null,
    @Json(name = "strImageSource")
    val strImageSource: String? = null,
    @Json(name = "strIngredient1")
    val strIngredient1: String? = null,
    @Json(name = "strIngredient10")
    val strIngredient10: String? = null,
    @Json(name = "strIngredient11")
    val strIngredient11: String? = null,
    @Json(name = "strIngredient12")
    val strIngredient12: String? = null,
    @Json(name = "strIngredient13")
    val strIngredient13: String? = null,
    @Json(name = "strIngredient14")
    val strIngredient14: String? = null,
    @Json(name = "strIngredient15")
    val strIngredient15: String? = null,
    @Json(name = "strIngredient16")
    val strIngredient16: String? = null,
    @Json(name = "strIngredient17")
    val strIngredient17: String? = null,
    @Json(name = "strIngredient18")
    val strIngredient18: String? = null,
    @Json(name = "strIngredient19")
    val strIngredient19: String? = null,
    @Json(name = "strIngredient2")
    val strIngredient2: String? = null,
    @Json(name = "strIngredient20")
    val strIngredient20: String? = null,
    @Json(name = "strIngredient3")
    val strIngredient3: String? = null,
    @Json(name = "strIngredient4")
    val strIngredient4: String? = null,
    @Json(name = "strIngredient5")
    val strIngredient5: String? = null,
    @Json(name = "strIngredient6")
    val strIngredient6: String? = null,
    @Json(name = "strIngredient7")
    val strIngredient7: String? = null,
    @Json(name = "strIngredient8")
    val strIngredient8: String? = null,
    @Json(name = "strIngredient9")
    val strIngredient9: String? = null,
    @Json(name = "strInstructions")
    val strInstructions: String? = null,
    @Json(name = "strMeal")
    val strMeal: String? = null,
    @Json(name = "strMealThumb")
    val strMealThumb: String? = null,
    @Json(name = "strMeasure1")
    val strMeasure1: String? = null,
    @Json(name = "strMeasure10")
    val strMeasure10: String? = null,
    @Json(name = "strMeasure11")
    val strMeasure11: String? = null,
    @Json(name = "strMeasure12")
    val strMeasure12: String? = null,
    @Json(name = "strMeasure13")
    val strMeasure13: String? = null,
    @Json(name = "strMeasure14")
    val strMeasure14: String? = null,
    @Json(name = "strMeasure15")
    val strMeasure15: String? = null,
    @Json(name = "strMeasure16")
    val strMeasure16: String? = null,
    @Json(name = "strMeasure17")
    val strMeasure17: String? = null,
    @Json(name = "strMeasure18")
    val strMeasure18: String? = null,
    @Json(name = "strMeasure19")
    val strMeasure19: String? = null,
    @Json(name = "strMeasure2")
    val strMeasure2: String? = null,
    @Json(name = "strMeasure20")
    val strMeasure20: String? = null,
    @Json(name = "strMeasure3")
    val strMeasure3: String? = null,
    @Json(name = "strMeasure4")
    val strMeasure4: String? = null,
    @Json(name = "strMeasure5")
    val strMeasure5: String? = null,
    @Json(name = "strMeasure6")
    val strMeasure6: String? = null,
    @Json(name = "strMeasure7")
    val strMeasure7: String? = null,
    @Json(name = "strMeasure8")
    val strMeasure8: String? = null,
    @Json(name = "strMeasure9")
    val strMeasure9: String? = null,
    @Json(name = "strSource")
    val strSource: String? = null,
    @Json(name = "strTags")
    val strTags: String? = null,
    @Json(name = "strYoutube")
    val strYoutube: String? = null
) {
    fun getIngredients(): List<IngredientModel> {
        val regexIngredient = """strIngredient(\d+)=(\w.*?[^,]*)""".toRegex()
        val regexMeasure = """strMeasure(\d+)=(\w.*?[^,]*)""".toRegex()
        val text = this.toString()
        val ingredients =
            regexIngredient.findAll(text).map { it.groupValues[1] to it.groupValues[2] }.toList()
        val measures =
            regexMeasure.findAll(text).map { it.groupValues[1] to it.groupValues[2] }.toList()
        return ingredients.map { i ->
            val measure = measures.find { m -> m.first == i.first }
            val imageUrl = "${IMAGE_BASE_URL}${i.second}.png"
            IngredientModel(i.second, measure?.second ?: "", imageUrl)
        }

    }

    fun getDirections(): List<DirectionModel> {
        val regex = """.*?\.""".toRegex()
        val text = this.strInstructions
        val directions = regex.findAll(text.toString())
            .map { it.value.trim().replace("\\r\\n", "") }.toList()
        return directions.mapIndexed { index, s -> DirectionModel(index, s) }
    }
}

data class IngredientModel(val name: String, val measure: String, val imageUrl: String = "")
data class DirectionModel(val id: Int, val description: String)