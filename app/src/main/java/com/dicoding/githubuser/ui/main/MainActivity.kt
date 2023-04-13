package com.dicoding.githubuser.ui.main

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Explode
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubuser.ui.ListUserAdapter
import com.dicoding.githubuser.R
import com.dicoding.githubuser.core.data.source.remote.response.ItemsItem
import com.dicoding.githubuser.databinding.ActivityMainBinding
import com.dicoding.githubuser.ui.favoriteUser.FavoriteUserActivity
import com.dicoding.githubuser.ui.setting.SettingViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class MainActivity : AppCompatActivity() {

    private var isDarkModeActive = false

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels {
        SettingViewModelFactory.getInstance(dataStore)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        with(window) {
            requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)

            // Set an exit transition
            exitTransition = Explode()
        }
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "GitHub User"

        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager

        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUser.addItemDecoration(itemDecoration)

        mainViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                this.isDarkModeActive = true
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

            } else {
                this.isDarkModeActive = false
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        mainViewModel.listUser.observe(this) { items ->
            setUserData(items)
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        mainViewModel.status.observe(this) {
            if (it == false) {
                Toast.makeText(this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        if (isDarkModeActive) {
            menu.getItem(0).setIcon(R.drawable.baseline_search_white_24)
            menu.getItem(1).setIcon(R.drawable.baseline_favorite_white_24)
            menu.getItem(2).setIcon(R.drawable.ic_light_mode)
        } else {
            menu.getItem(0).setIcon(R.drawable.baseline_search_24)
            menu.getItem(1).setIcon(R.drawable.ic_favorite)
            menu.getItem(2).setIcon(R.drawable.ic_night_mode)
        }

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                mainViewModel.searchUser(query)
                searchView.clearFocus()
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favorite -> {
                val intent = Intent(this, FavoriteUserActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.night_mode -> {
                if (!isDarkModeActive) {
                    this.isDarkModeActive = true
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    this.isDarkModeActive = false
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
                mainViewModel.saveThemeSetting(isDarkModeActive)
                return true
            }
            else -> return true
        }
    }

    private fun setUserData(userData: List<ItemsItem>) {
        val adapter = ListUserAdapter(userData)
        binding.rvUser.adapter = adapter
    }


    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}