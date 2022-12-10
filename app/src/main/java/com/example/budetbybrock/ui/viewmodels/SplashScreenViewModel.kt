package com.example.budetbybrock.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.budetbybrock.ui.repositories.UserRepository

class SplashScreenViewModel (application: Application): AndroidViewModel(application){
    fun isUserLoggedIn() = UserRepository.isUserLoggedIn()
}