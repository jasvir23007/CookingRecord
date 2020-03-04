package com.jasvir.cookingrecord.ui.home.adapter

import androidx.paging.PagedListAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jasvir.cookingrecord.R
import com.jasvir.cookingrecord.extensions.load
import com.jasvir.cookingrecord.model.Character
import com.jasvir.cookingrecord.navigation.Navigator
import kotlinx.android.synthetic.main.item_record.view.*

class CookingRecordAdapter(private val navigator: Navigator) :
    PagedListAdapter<Character, CookingRecordAdapter.ViewHolder>(characterDiff) {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.item_record, p0, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val character = getItem(position)
        holder.txtName.text = character?.recipe_type
        holder.imgThumbnail.load("${character?.image_url}")
        holder.txtComment.text = character?.comment
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        override fun onClick(p0: View?) {
            navigator.navigateToDetails(getItem(adapterPosition)!!)
        }

        val imgThumbnail = itemView.imgThumbnail
        val txtName = itemView.txtName
        val txtComment = itemView.txtComment
        init {
            itemView.setOnClickListener(this)
        }

    }

    companion object {
        val characterDiff = object : DiffUtil.ItemCallback<Character>() {
            override fun areItemsTheSame(old: Character, new: Character): Boolean {
                return old.id == new.id
            }

            override fun areContentsTheSame(old: Character, new: Character): Boolean {
                return old == new
            }
        }
    }
}
