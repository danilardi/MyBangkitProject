package com.dicoding.githubuser.ui.favoriteUser

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.githubuser.R
import com.dicoding.githubuser.core.data.source.local.entity.FavoriteUserEntity
import com.dicoding.githubuser.ui.ListUserAdapter
import com.dicoding.githubuser.ui.detailUser.DetailUserActivity

class ListFavoriteUserAdapter(private val listUser: List<FavoriteUserEntity>) : ListAdapter<FavoriteUserEntity, ListFavoriteUserAdapter.ViewHolder >(DIFF_CALLBACK) {
    
    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val ivProfile: ImageView = view.findViewById(R.id.iv_profile)
        val tvUsername: TextView = view.findViewById(R.id.tv_username)
        val tvDesc: TextView = view.findViewById(R.id.tv_desc)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false))

    override fun getItemCount() = listUser.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvUsername.text = listUser[position].username
        holder.tvDesc.text = listUser[position].htmlUrl
        Glide.with(holder.ivProfile)
            .load(listUser[position].avatarUrl)
            .apply(RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error))
            .into(holder.ivProfile)
        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context, DetailUserActivity::class.java)
            intent.putExtra(DetailUserActivity.EXTRA_USER, listUser[position].username)
            holder.itemView.context.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(
                holder.itemView.context as Activity?
            ).toBundle())
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<FavoriteUserEntity> =
            object : DiffUtil.ItemCallback<FavoriteUserEntity>() {
                override fun areItemsTheSame(oldUser: FavoriteUserEntity, newUser: FavoriteUserEntity): Boolean {
                    return oldUser.username == newUser.username
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(oldUser: FavoriteUserEntity, newUser: FavoriteUserEntity): Boolean {
                    return oldUser == newUser
                }
            }
    }
}