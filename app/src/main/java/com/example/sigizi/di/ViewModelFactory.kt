package com.example.sigizi.di

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sigizi.view.artikel.ArtikelViewModel
import com.example.sigizi.view.artikel.ArticlePostViewModel
import com.example.sigizi.view.forum.DetailForumViewModel
import com.example.sigizi.view.forum.ForumPostViewModel
import com.example.sigizi.view.forum.ForumViewModel
import com.example.sigizi.view.home.HomeViewModel
import com.example.sigizi.view.konsultasi.KonsultasiViewModel
import com.example.sigizi.view.login.LoginViewModel
import com.example.sigizi.view.main.MainViewModel
import com.example.sigizi.view.signup.SignUpViewModel
import com.example.sigizi.view.splash.SplashViewModel

class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(Injection.provideUserRepository(context)) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(Injection.provideUserRepository(context)) as T
            }
            modelClass.isAssignableFrom(SignUpViewModel::class.java) -> {
                SignUpViewModel(Injection.provideUserRepository(context)) as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(Injection.provideUserRepository(context)) as T
            }
            modelClass.isAssignableFrom(SplashViewModel::class.java) -> {
                SplashViewModel(Injection.provideUserRepository(context)) as T
            }
            modelClass.isAssignableFrom(ArtikelViewModel::class.java) -> {
                ArtikelViewModel(Injection.provideArticleRepository(context)) as T
            }
            modelClass.isAssignableFrom(ArticlePostViewModel::class.java) -> {
                ArticlePostViewModel(Injection.provideArticleRepository(context)) as T
            }
            modelClass.isAssignableFrom(KonsultasiViewModel::class.java) -> {
                KonsultasiViewModel(Injection.provideSurveyRepository(context)) as T
            }
            modelClass.isAssignableFrom(ForumViewModel::class.java) -> {
                ForumViewModel(Injection.provideForumRepository(context)) as T
            }
            modelClass.isAssignableFrom(ForumPostViewModel::class.java) -> {
                ForumPostViewModel(Injection.provideForumRepository(context)) as T
            }
            modelClass.isAssignableFrom(DetailForumViewModel::class.java) -> {
                DetailForumViewModel(Injection.provideForumRepository(context)) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}
