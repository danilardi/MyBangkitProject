package com.dicoding.githubuser.ui.favoriteUser

import android.os.Bundle
import android.transition.Explode
import android.util.Log
import android.view.View
import android.view.Window
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubuser.core.data.source.local.entity.FavoriteUserEntity
import com.dicoding.githubuser.databinding.ActivityFavoriteUserBinding
import com.dicoding.githubuser.ui.ViewModelFactory

class FavoriteUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteUserBinding
    private val favUserViewModel: FavoriteUserViewModel by viewModels {
        ViewModelFactory.getInstance(application)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        with(window) {
            requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)

            // Set an exit transition
            exitTransition = Explode()
        }
        super.onCreate(savedInstanceState)

        binding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Favorite User"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager

        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUser.addItemDecoration(itemDecoration)

        favUserViewModel.getAllFavUser().observe(this) {items ->
            binding.progressBar.visibility = View.GONE
            setUserData(items)
        }
    }

    private fun setUserData(userData: List<FavoriteUserEntity>) {
        val adapter = ListFavoriteUserAdapter(userData)
        binding.rvUser.adapter = adapter
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}