package ifpe.mobile.lactgoGo.src.database.models
import com.google.firebase.firestore.PropertyName

data class RestaurantModel(
    var id: String = "",
    @PropertyName("name") var name: String = "",
    @PropertyName("address") var address: AddressModel? = null,
    @PropertyName("rating") val rating: Double = 0.0,
    @PropertyName("contacts") var contacts: List<String> = emptyList(),
    @PropertyName("openingTime") var openingTime: Int = 8,
    @PropertyName("closingTime") var closingTime: Int = 17,
    @PropertyName("tags") val tags: List<String> = emptyList(),
    @PropertyName("menu") val menu: List<DishModel> = emptyList(),
    @PropertyName("comments") val comments: List<String> = emptyList(),
    @PropertyName("logo") val logo: String = ""
)
