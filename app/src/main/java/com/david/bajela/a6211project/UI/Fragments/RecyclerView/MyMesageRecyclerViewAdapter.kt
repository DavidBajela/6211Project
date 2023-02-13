package com.david.bajela.a6211project.UI.Fragments.RecyclerView

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.text.format.DateFormat
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.david.bajela.a6211project.Data.Note
import com.david.bajela.a6211project.R

class MyMesageRecyclerViewAdapter(
    private val values: List<Note>, m: messageListerner
) : RecyclerView.Adapter<MyMesageRecyclerViewAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.note_item, parent, false)
        return ViewHolder(view)
    }

    var mi: messageListerner = m

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        val c = holder.itemView.context
        val d: Bitmap = BitmapFactory.decodeResource(c.resources, item.iconName)

        holder.itemView.setOnClickListener { mi.onMessageclicked(holder.adapterPosition) }
        holder.icaon.setImageBitmap(d)
        holder.message.text = item.note
        holder.title.text = item.title
        holder.place = position
        val f = "HH:mm:ss dd/MM/yyyy"
        val date = DateFormat.format(f, item.lastUpdate)
        holder.timeS.text = date
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var place: Int = 0
        val icaon: ImageView = itemView.findViewById(R.id.not_iv_icon)
        val title: TextView = itemView.findViewById(R.id.not_tv_Title)
        val message: TextView = itemView.findViewById(R.id.not_mlet_message)
        val timeS: TextView = itemView.findViewById(R.id.not_tv_timestamp)
    }

    interface messageListerner {
        fun onMessageclicked(position: Int)
    }

}