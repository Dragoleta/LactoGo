package ifpe.mobile.lactgoGo.src.database.db

import FBUser
import User
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.firestore
import ifpe.mobile.lactgoGo.src.database.models.DishModel

import ifpe.mobile.lactgoGo.src.database.models.RestaurantModel

import toFBUser
import toUser

class FirebaseDB(private val listener: Listener? = null) {
    private val auth = Firebase.auth
    private val db = Firebase.firestore
    interface Listener {
        fun onUserLoaded(user: User)
        fun setRestaurants(rests: List<RestaurantModel>)
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


     fun getRestaurants() {
        val restaurantsRef = db.collection("restaurants")
        restaurantsRef.get().addOnSuccessListener { querySnapshot ->
            val restaurants = querySnapshot.documents.mapNotNull { document ->
                val restaurant = document.toObject(RestaurantModel::class.java)
                    restaurant?.copy(id = document.id)
            }
            listener?.setRestaurants(restaurants)
        }.addOnFailureListener { exception ->
            Log.e("FirebaseDB", "Error fetching restaurants", exception)
        }
    }


    fun saveRestaurant(restaurant: RestaurantModel) {
        try {
            db.collection("restaurants").document().set(restaurant)
                .addOnSuccessListener {
                    Log.d("Firestore", "Document successfully written!")
                }
                .addOnFailureListener { e ->
                    Log.w("Firestore", "Error writing document", e)
                }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun updateUser(user: User) {
        if (auth.currentUser == null) {
            throw RuntimeException("User not logged in!")
        }
        val uid = auth.currentUser!!.uid
        db.collection("users").document(uid).set(user.toFBUser())
            .addOnSuccessListener {
                Log.d("FirebaseDB", "User updated successfully!")
            }
            .addOnFailureListener { exception ->
                Log.e("FirebaseDB", "Error updating user", exception)
            }
    }

    fun addDishToRestaurant(
        dish:DishModel, restaurantId:String) {
        try {
            db.collection("restaurants")
                .document( restaurantId )
                .update("menu", FieldValue.arrayUnion(dish))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun addCommentToRestaurant(
        comment : String, restaurantId : String) {
        try {
            db.collection("restaurants")
                .document( restaurantId )
                .update("comments", FieldValue.arrayUnion(comment))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


}
