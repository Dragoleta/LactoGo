package ifpe.mobile.lactgoGo.src.database.models

data class Restaurant(
// Add a logo to the restaurant
//    val logo:
    val name: String,
    val address: String,
    val rating: String,
    val contacts: List<String>,
    val openingTime: String = "08:00",
    val closingTime: String = "17:00",
    val tags: List<String>,

    val menu: List<Dish>,

    val comments: List<String>,

)