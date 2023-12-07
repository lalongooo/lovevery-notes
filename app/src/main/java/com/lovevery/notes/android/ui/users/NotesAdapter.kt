package com.lovevery.notes.android.ui.users

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lovevery.notes.android.R

class NotesAdapter(
    private val notes: List<String>,
    private val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NoteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.notes_recycler_view_item,
                parent,
                false
            )
        return NoteViewHolder(view) { position ->
            onItemClick(notes[position])
        }
    }

    override fun onBindViewHolder(
        holder: NoteViewHolder,
        position: Int
    ): Unit = holder.bind(notes[position])

    override fun getItemCount(): Int = notes.size

    class NoteViewHolder(
        view: View,
        onItemClicked: (Int) -> Unit
    ) : RecyclerView.ViewHolder(view) {

        init {
            view.setOnClickListener {
                onItemClicked(adapterPosition)
            }
        }

        private val textViewUserName: TextView =
            view.findViewById(R.id.textView_item)

        fun bind(note: String) {
            textViewUserName.text = note
        }
    }
}