package com.icedigital.exerclock.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.icedigital.exerclock.data.PerfilViewModel.Companion.USERS_COLLECTION

class RegisterViewModel : ViewModel() {

    private val _registrationState = MutableLiveData<RegistrationState>()
    val registrationState: LiveData<RegistrationState> = _registrationState

    private val auth = FirebaseAuth.getInstance()

    fun register(name: String, email: String, password: String) {
        if (name.isBlank() || email.isBlank() || password.isBlank()) {
            _registrationState.value = RegistrationState.ERROR
        } else {
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    val profileUpdates = UserProfileChangeRequest.Builder()
                        .setDisplayName(name)
                        .build()
                    user?.updateProfile(profileUpdates)?.addOnCompleteListener { profileUpdateTask ->
                        if (profileUpdateTask.isSuccessful) {
                            // Aquí guardamos el nombre del usuario en Firestore
                            val db = FirebaseFirestore.getInstance()
                            val userId = user.uid
                            val userDocumentRef = db.collection(USERS_COLLECTION).document(userId)
                            userDocumentRef.set(mapOf("userName" to name)).addOnCompleteListener { firestoreTask ->
                                _registrationState.value = if (firestoreTask.isSuccessful) {
                                    RegistrationState.SUCCESS
                                } else {
                                    RegistrationState.ERROR
                                }
                            }
                        } else {
                            _registrationState.value = RegistrationState.ERROR
                        }
                    }
                } else {
                    _registrationState.value = RegistrationState.ERROR
                }
            }
        }
    }
    fun resetRegistrationState() {
        _registrationState.value = null // o cualquier otro valor neutral
    }
    enum class RegistrationState {
        SUCCESS,
        ERROR
    }
}
