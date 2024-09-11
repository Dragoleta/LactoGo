package ifpe.mobile.lactgoGo.src.MVM

import User
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import ifpe.mobile.lactgoGo.src.database.db.FirebaseDB
import ifpe.mobile.lactgoGo.src.database.models.Restaurant


class MainViewModel : ViewModel(),  FirebaseDB.Listener {

    private val _user = mutableStateOf (User(name="", email = "", savedAddress = null))


    private var _restaurants = mutableStateListOf<Restaurant>()

    val user : User
        get() = _user.value

    private var _loggedIn = mutableStateOf(false)

    val loggedIn : Boolean
        get() = _loggedIn.value

    private val listener = FirebaseAuth.AuthStateListener {
            firebaseAuth -> _loggedIn.value = firebaseAuth.currentUser != null
    }

    init {
        listener.onAuthStateChanged(Firebase.auth)
        Firebase.auth.addAuthStateListener(listener)
    }

    val restaurants : List<Restaurant> get() = _restaurants

    override fun onUserLoaded(user: User) {
        _user.value = user
    }

    override fun setRestaurants(rests: List<Restaurant>) {
        _restaurants.clear()
        _restaurants.addAll(rests)
    }
//    override fun onCityAdded(city: City) {
//        _cities.add(city)
//    }
//    override fun onCityRemoved(city: City) {
//        _cities.remove(city)
//    }
}
