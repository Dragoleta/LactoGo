import ifpe.mobile.lactgoGo.src.database.models.AddressModel

data class User(
    val name: String,
    val email: String,
    val savedAddress: AddressModel? = null,
    val restrictions: List<String>? = null
)

class FBUser {
    var name: String? = null
    var savedAddress: AddressModel? = null
    var email: String? = null
    var restrictions: List<String>? = null
}

fun FBUser.toUser(): User? {
    val name = this.name ?: return null
    val email = this.email ?: return null
    val savedAddress = this.savedAddress
    val restrictions = this.restrictions

    return User(name, email, savedAddress, restrictions)
}

fun User.toFBUser(): FBUser {
    return FBUser().apply {
        name = this@toFBUser.name
        savedAddress = this@toFBUser.savedAddress
        email = this@toFBUser.email
        restrictions = this@toFBUser.restrictions
    }
}
