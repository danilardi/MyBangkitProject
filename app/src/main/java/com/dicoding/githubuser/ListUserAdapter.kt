package com.dicoding.githubuser

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.githubuser.core.data.source.remote.response.ItemsItem
import com.dicoding.githubuser.ui.detailUser.DetailUserActivity

class ListUserAdapter(private val listUser: List<ItemsItem>) : RecyclerView.Adapter<ListUserAdapter.ViewHolder>() {

    companion object {
        private val TAG = "ListUserAdapter"
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val ivProfile: ImageView = view.findViewById(R.id.iv_profile)
        val tvUsername: TextView = view.findViewById(R.id.tv_username)
        val tvDesc: TextView = view.findViewById(R.id.tv_desc)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false))

    override fun getItemCount() = listUser.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvUsername.text = listUser[position].login
        holder.tvDesc.text = listUser[position].htmlUrl
        Glide.with(holder.ivProfile)
            .load(listUser[position].avatarUrl)
            .into(holder.ivProfile)
        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context, DetailUserActivity::class.java)
            intent.putExtra(DetailUserActivity.EXTRA_USER, listUser[position].login)
            holder.itemView.context.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(
                holder.itemView.context as Activity?
            ).toBundle())
        }
    }
}