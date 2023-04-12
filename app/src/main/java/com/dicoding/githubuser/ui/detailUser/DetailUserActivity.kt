package com.dicoding.githubuser.ui.detailUser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.dicoding.githubuser.R
import com.dicoding.githubuser.ui.SectionPagerAdapter
import com.dicoding.githubuser.core.data.source.remote.response.DetailUserResponse
import com.dicoding.githubuser.databinding.ActivityDetailUserBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {
    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2,
        )
        const val EXTRA_USER = "extra_user"
    }

    private lateinit var binding: ActivityDetailUserBinding
    private val detailUserViewModel: DetailUserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userId = intent.getStringExtra(EXTRA_USER)

        val sectionPagerAdapter = SectionPagerAdapter(this, userId)
        val viewPager: ViewPager2 = findViewById(R.id.vp_landing)
        viewPager.adapter = sectionPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tl_landing)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.title = "Detail User"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        detailUserViewModel.getUser(userId!!)

        detailUserViewModel.user.observe(this) { user ->
            setUserData(user)
        }

        detailUserViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        detailUserViewModel.status.observe(this) {
            if (it == false) {
                Toast.makeText(this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setUserData(user: DetailUserResponse) {
        binding.tvName.text = user.name
        binding.tvUsername.text = user.login
        if(user.bio != null)
            binding.tvDesc.text = user.bio.toString()
        else
            binding.tvDesc.text = ""
        Glide.with(this@DetailUserActivity)
            .load(user.avatarUrl)
            .into(binding.ivProfile)
        TabLayoutMediator(binding.tlLanding, binding.vpLanding) { tab, position ->
            if (position == 0)
                tab.text = resources.getString(TAB_TITLES[position]) + " " + user.followers.toString()
            else if (position == 1)
                tab.text = resources.getString(TAB_TITLES[position]) + " " + user.following.toString()
        }.attach()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}