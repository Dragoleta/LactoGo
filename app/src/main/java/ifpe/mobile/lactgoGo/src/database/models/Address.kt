package ifpe.mobile.lactgoGo.src.database.models

import com.google.firebase.firestore.PropertyName

// TODO: Add longitude e latitude
data class AddressModel(
    @PropertyName("street") var street: String = "",
    @PropertyName("number") var number: Int = 0,
    @PropertyName("district") var district: String = "",
    @PropertyName("city") var city: String = "",
    @PropertyName("state") var state: String = "",
    @PropertyName("country") var country: String = "brazil",
)
