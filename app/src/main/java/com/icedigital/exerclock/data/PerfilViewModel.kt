package com.icedigital.exerclock.data

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class PerfilViewModel : ViewModel() {
    companion object {
        const val USERS_COLLECTION = "users"
        const val COVER_PICTURE_URL_FIELD = "coverPictureUrl"
        const val PROFILE_PICTURE_URL_FIELD = "profilePictureUrl"
    }

    private val db = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private val userId = auth.currentUser?.uid
        ?: throw IllegalStateException("No user is currently logged in")
    private val userDocumentRef = db.collection(USERS_COLLECTION).document(userId)

    val userName = MutableStateFlow("")
    val description = MutableStateFlow("")
    val profilePictureUrl = MutableStateFlow("")
    val coverPictureUrl = MutableStateFlow("")
    val error = MutableStateFlow("")

    init {
        loadUserInfo()
    }

    private fun loadUserInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val docSnapshot = userDocumentRef.get().await()
                userName.value = docSnapshot.getStringField("userName")
                description.value = docSnapshot.getStringField("description")
                profilePictureUrl.value = docSnapshot.getStringField(PROFILE_PICTURE_URL_FIELD)
                coverPictureUrl.value = docSnapshot.getStringField(COVER_PICTURE_URL_FIELD)
            } catch (e: FirebaseFirestoreException) {
                error.value = e.message ?: ""
            }
        }
    }

    fun saveCoverPicture(coverFile: Uri) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val coverRef = storage.reference.child("covers/${coverFile.lastPathSegment}")
                coverRef.putFile(coverFile).await()
                val coverUrl = coverRef.downloadUrl.await().toString()
                updateOrCreateUserDocument(COVER_PICTURE_URL_FIELD, coverUrl)
                coverPictureUrl.value = coverUrl
            } catch (e: Exception) {
                error.value = e.message ?: ""
            }
        }
    }

    fun saveProfilePicture(profileFile: Uri) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val profileRef = storage.reference.child("profiles/${profileFile.lastPathSegment}")
                profileRef.putFile(profileFile).await()
                val profileUrl = profileRef.downloadUrl.await().toString()
                updateOrCreateUserDocument(PROFILE_PICTURE_URL_FIELD, profileUrl)
                profilePictureUrl.value = profileUrl
            } catch (e: Exception) {
                error.value = e.message ?: ""            }
        }
    }

    fun saveUserName(newUserName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                updateOrCreateUserDocument("userName", newUserName)
                userName.value = newUserName
            } catch (e: FirebaseFirestoreException) {
                error.value = e.message ?: ""
            }
        }
    }

    fun saveDescription(newDescription: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                userDocumentRef.update("description", newDescription).await()
                description.value = newDescription
            } catch (e: FirebaseFirestoreException) {
                error.value = e.message ?: ""
            }
        }
    }

    private suspend fun updateOrCreateUserDocument(fieldName: String, value: Any) {
        val snapshot = userDocumentRef.get().await()
        if (snapshot.exists()) {
            userDocumentRef.update(fieldName, value).await()
        } else {
            userDocumentRef.set(mapOf(fieldName to value)).await()
        }
    }

    private fun DocumentSnapshot.getStringField(fieldName: String): String {
        return this.getString(fieldName) ?: ""
    }
}
