package ifpe.mobile.lactgoGo.src.database.models

data class Dish(
    val name: String,
    val price: Float,
    val description: String,
    val ingredients: List<String>
)