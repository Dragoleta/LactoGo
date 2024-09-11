package ifpe.mobile.lactgoGo.src.database.db

import FBUser
import User
import android.util.Log
import androidx.compose.runtime.Composable
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import ifpe.mobile.lactgoGo.src.database.models.Restaurant
import toFBUser
import toUser

class FirebaseDB(private val listener: Listener? = null) {
    private val auth = Firebase.auth
    private val db = Firebase.firestore
    interface Listener {
        fun onUserLoaded(user: User)
        fun setRestaurants(rests: List<Restaurant>)
//        fun onCityAdded(city: City)
//        fun onCityRemoved(city: City)
    }

    init {
        auth.addAuthStateListener { auth ->
            if (auth.currentUser == null) {
                return@addAuthStateListener
            }
            val refCurrUser = db.collection("users")
                .document(auth.currentUser!!.uid)
            refCurrUser.get().addOnSuccessListener {
                it.toObject(FBUser::class.java)?.let { user ->
                    user.toUser()?.let { it1 -> listener?.onUserLoaded(it1) }
                }
            }
            getRestaurants()
    }}


     fun register(user: User) {
         if ( auth.currentUser == null) {
             throw RuntimeException("User not logged in!")
         }
        val uid = auth.currentUser!!.uid
        db.collection("Users").document(uid + "").set(user.toFBUser())
     }


    private fun getRestaurants() {
        val restaurantsRef = db.collection("restaurants")
        restaurantsRef.get().addOnSuccessListener { querySnapshot ->
            val restaurants = querySnapshot.documents.mapNotNull { it.toObject(Restaurant::class.java) }
            listener?.setRestaurants(restaurants)
        }.addOnFailureListener { exception ->
            Log.e("FirebaseDB", "Error fetching restaurants", exception)
        }
    }


    fun saveRestaurant(restaurant: Restaurant) {
        try {
            db.collection("restaurants").document().set(restaurant)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


}
