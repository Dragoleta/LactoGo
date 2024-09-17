data class User(
    val name: String,
    val email: String,
    val savedAddress: List<String>? = null,
)

class FBUser {
    var name: String? = null
    var savedAddress: List<String>? = null
    var email: String? = null
//    var userType: Boolean
}

fun FBUser.toUser(): User? {
    val name = this.name ?: return null
    val email = this.email ?: return null
    val savedAddress = this.savedAddress

    return User(name, email, savedAddress )
}

fun User.toFBUser(): FBUser {
    return FBUser().apply {
        name = this@toFBUser.name
        savedAddress = this@toFBUser.savedAddress
        email = this@toFBUser.email
    }
}
