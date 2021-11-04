package com.jwhh.notekeeper

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.jwhh.notekeeper.data.model.NoteInfo

class CommentRecyclerAdapter(val context: Context, val note: NoteInfo) : RecyclerView.Adapter<CommentRecyclerAdapter.ViewHolder>() {
  private val layoutInflater = LayoutInflater.from(context)

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val itemView = layoutInflater.inflate(R.layout.item_comment, parent, false)
    return ViewHolder(itemView)
  }

  override fun getItemCount(): Int {
    return note.comments.size
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.name.text = note.comments[position].name?:"You"
    holder.comment.text = note.comments[position].comment
  }

  inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val name: TextView = itemView.findViewById(R.id.name)
    val comment: TextView = itemView.findViewById(R.id.comment)
  }
}