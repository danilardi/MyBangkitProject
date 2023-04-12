package com.dicoding.githubuser.ui.favoriteUser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.githubuser.R
import com.dicoding.githubuser.databinding.ActivityFavoriteUserBinding

class FavoriteUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_user)
    }
}