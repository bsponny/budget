package com.example.budetbybrock.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.budetbybrock.ui.repositories.UserRepository

class RootViewModel (application: Application): AndroidViewModel(application){
    fun getUserEmail() = UserRepository.getCurrentUserEmail()
    fun signOutUser() = UserRepository.signOutUser()
}