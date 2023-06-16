package com.herbify.herbifyapp.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.herbify.herbifyapp.data.remote.response.article.ArticleData
import com.herbify.herbifyapp.data.remote.response.auth.UserData
import com.herbify.herbifyapp.databinding.ItemPostBinding
import com.herbify.herbifyapp.ui.herbal_talk.detail.DetailPostActivity
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ArticleAdapter(private val articles: List<ArticleData>, private val users: List<UserData>) : RecyclerView.Adapter<ArticleAdapter.MyViewHolder>() {
    class MyViewHolder(val binding: ItemPostBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int = articles.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        Glide.with(holder.itemView).load(users[position].photo).into(holder.binding.ivProfil)
        Glide.with(holder.itemView).load(articles[position].article?.photo).into(holder.binding.ivPicturePost)

        holder.binding.tvFavorite.text = articles[position].numLike.toString()
//        holder.binding.tvAddress.text = users[position].name
        holder.binding.tvName.text = articles[position].article?.title
        holder.binding.tvDescription.text = articles[position].article?.content

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailPostActivity::class.java)
            intent.putExtra("id", articles[position].article?.id)
            holder.itemView.context.startActivity(intent)
        }
    }
}