package ifpe.mobile.lactgoGo.src.database.models




data class DishModel(
    var name: String = "",
    var price: Double = 0.0,
    var description: String = "",
    var ingredients: List<String> = emptyList(),
)

fun DishModel.toFBDish() : FBDish {
    return FBDish().apply {
        name = this@toFBDish.name
        description = this@toFBDish.description
        price = this@toFBDish.price
        ingredients = this@toFBDish.ingredients
    }
}

class FBDish {
    var name: String? = null
    var price: Double? = null
    var description: String? = null
    var ingredients: List<String>? = null

    fun toDishModel(): DishModel {
        return DishModel(
            name = name ?: "",
            price = price ?: 0.0,
            description = description ?: "",
            ingredients = ingredients ?: emptyList()
        )
    }
}

