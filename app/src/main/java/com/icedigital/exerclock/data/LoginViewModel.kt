package com.icedigital.exerclock.data

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.icedigital.exerclock.navigation.Destinations

class LoginViewModel : ViewModel() {

    private val _authResultCode = MutableLiveData<AuthResultCode>()
    val authResultCode: LiveData<AuthResultCode> = _authResultCode

    private val _isAnonymousUser = MutableLiveData<Boolean>()
    val isAnonymousUser: LiveData<Boolean> = _isAnonymousUser

    private val auth = FirebaseAuth.getInstance()

    val navigationEvent = MutableLiveData<String>()

    fun resetAuthResultCode() {
        _authResultCode.value = null // o cualquier otro valor neutral
    }

    fun buildLoginIntent(): Intent {
        return AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(
                listOf(
                    AuthUI.IdpConfig.EmailBuilder().build(),
                    AuthUI.IdpConfig.GoogleBuilder().build()
                )
            )
            .build()
    }

    fun handleLoginResult(resultCode: Int, data: Intent?) {
        val response = IdpResponse.fromResultIntent(data)
        if (resultCode == Activity.RESULT_OK) {
            // Successfully signed in
            _authResultCode.value = AuthResultCode.SUCCESS
            checkUserState()
        } else {
            // Sign in failed
            if (response == null) {
                // User pressed back button
                _authResultCode.value = AuthResultCode.CANCELLED
            } else {
                // Some error occurred
                _authResultCode.value = AuthResultCode.ERROR
            }
        }
    }

    fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success
                    _authResultCode.value = AuthResultCode.SUCCESS
                    checkUserState()
                } else {
                    // Sign in failed
                    _authResultCode.value = AuthResultCode.ERROR
                }
            }
    }


    fun checkUserState() {
        val user = auth.currentUser
        if (user != null) {
            _isAnonymousUser.value = user.isAnonymous
            // Navigate to Home Screen
        } else {
            // No user is signed in, show a message to the user
            _authResultCode.value = AuthResultCode.CANCELLED
        }
    }
}

enum class AuthResultCode {
    SUCCESS,
    CANCELLED,
    ERROR
}
