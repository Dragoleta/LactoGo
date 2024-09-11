package ifpe.mobile.lactgoGo.src.database.models
import com.google.firebase.firestore.PropertyName

data class Restaurant(
    var id: String = "",
    @PropertyName("name") val name: String = "",
    @PropertyName("address") val address: String = "",
    @PropertyName("rating") val rating: Double = 0.0,
    @PropertyName("contacts") val contacts: List<String> = emptyList(),
    @PropertyName("openingTime") val openingTime: String = "08:00",
    @PropertyName("closingTime") val closingTime: String = "17:00",
    @PropertyName("tags") val tags: List<String> = emptyList(),
    @PropertyName("menu") val menu: Map<String, Dish> = emptyMap(),
    @PropertyName("comments") val comments: List<String> = emptyList(),
    @PropertyName("logo") val logo: String = ""
)
