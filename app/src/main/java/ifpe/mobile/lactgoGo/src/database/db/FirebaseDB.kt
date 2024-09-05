package ifpe.mobile.lactgoGo.src.database.db

import User
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import toFBUser

class FirebaseDB(private val listener: Listener? = null) {
    private val auth = Firebase.auth
    private val db = Firebase.firestore
    interface Listener {
    }

////    private var citiesListReg: ListenerRegistration? = null
//    interface Listener {
////        fun onUserLoaded(user: User)
////        fun onCityAdded(city: City)
////        fun onCityRemoved(city: City)
//    }

//    init {
//        auth.addAuthStateListener { auth ->
//            if (auth.currentUser == null) {
////                citiesListReg?.remove()
//                return@addAuthStateListener
//            }
//            val refCurrUser = db.collection("users")
//                .document(auth.currentUser!!.uid)
//            refCurrUser.get().addOnSuccessListener {
//                it.toObject(FBUser::class.java)?.let { user ->
//                    listener?.onUserLoaded(user.toUser())
//                }
//            }

//
//        }
//    }
     fun register(user: User) {
         if ( auth.currentUser == null) {
             throw RuntimeException("User not logged in!")
         }
        val uid = auth.currentUser!!.uid
        db.collection("Users").document(uid + "").set(user.toFBUser());
     }
}
