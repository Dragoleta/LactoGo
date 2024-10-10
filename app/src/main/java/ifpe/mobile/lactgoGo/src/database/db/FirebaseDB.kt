package ifpe.mobile.lactgoGo.src.database.db

import FBUser
import User
import android.annotation.SuppressLint
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FieldValue

import com.google.firebase.firestore.firestore
import ifpe.mobile.lactgoGo.src.database.models.DishModel

import ifpe.mobile.lactgoGo.src.database.models.RestaurantModel

import toFBUser
import toUser

@SuppressLint("MissingPermission")
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
            val refCurrUser = db.collection("Users")
                .document(auth.currentUser!!.uid)
            refCurrUser.get().addOnSuccessListener {
                it.toObject(FBUser::class.java)?.let { user ->
                    user.toUser()?.let { it1 -> listener?.onUserLoaded(it1) }
                }
            }

            getRestaurantsWithParams(variable = "recife", queryParam = "address.city")
        }
    }

    fun reauthenticateUser(email: String, oldPassword: String, onComplete: (Boolean) -> Unit) {
        val user = auth.currentUser
        if (user != null) {
            val credential = EmailAuthProvider.getCredential(email, oldPassword)

            user.reauthenticate(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        onComplete(true)
                    } else {
                        onComplete(false)
                    }
                }
                .addOnFailureListener { exception ->
                    Log.e("FirebaseDB", "Error reauthenticating user", exception)
                    onComplete(false)
                }
        } else {
            onComplete(false)
        }
    }

    fun updatePassword(newPassword: String, onComplete: (Boolean) -> Unit) {
        val user = auth.currentUser
        user?.updatePassword(newPassword)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onComplete(true)
                } else {
                    onComplete(false)
                }
            }
            ?.addOnFailureListener { exception ->
                Log.e("FirebaseDB", "Error updating password", exception)
                onComplete(false)
            }
    }


    fun register(user: User) {
        if (auth.currentUser == null) {
            throw RuntimeException("User not logged in!")
        }
        val uid = auth.currentUser!!.uid
        db.collection("Users").document(uid + "").set(user.toFBUser())
    }

    fun getRestaurantsWithParams(variable: String, queryParam: String) {
        val restaurantsRef = db.collection("restaurants")
        restaurantsRef.whereEqualTo(queryParam, variable).get().addOnSuccessListener { querySnapshot ->
            val restaurants = querySnapshot.documents.mapNotNull { document ->
                val restaurant = document.toObject(RestaurantModel::class.java)
                restaurant?.copy(id = document.id)
            }
            listener?.setRestaurants(restaurants)
        }.addOnFailureListener { exception ->
            Log.e("FirebaseDB", "Error fetching restaurants", exception)
        }
    }



    fun getUser(onComplete: (User?) -> Unit) {
        if (auth.currentUser == null) {
            onComplete(null)
            return
        }
        val uid = auth.currentUser!!.uid
        db.collection("Users").document(uid).get()
            .addOnSuccessListener { document ->
                val fbUser = document.toObject(FBUser::class.java)
                onComplete(fbUser?.toUser())
            }
            .addOnFailureListener { exception ->
                Log.e("FirebaseDB", "Error fetching user", exception)
                onComplete(null)
            }
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

    fun updateUser(user: User) {
        val auth = FirebaseAuth.getInstance()

        if (auth.currentUser == null) {
            throw RuntimeException("User not logged in!")
        }
        val uid = auth.currentUser!!.uid
        db.collection("Users").document(uid).set(user.toFBUser())
            .addOnSuccessListener {
                Log.d("FirebaseDB", "User updated successfully!")
            }
            .addOnFailureListener { exception ->
                Log.e("FirebaseDB", "Error updating user", exception)
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


    fun addDishToRestaurant(
        dish: DishModel, restaurantId: String
    ) {
        try {
            db.collection("restaurants")
                .document(restaurantId)
                .update("menu", FieldValue.arrayUnion(dish))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun addCommentToRestaurant(
        comment: String, restaurantId: String
    ) {
        try {
            db.collection("restaurants")
                .document(restaurantId)
                .update("comments", FieldValue.arrayUnion(comment))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
