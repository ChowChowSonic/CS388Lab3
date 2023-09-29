package com.example.cs388lab3.ui.planets

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cs388lab3.R

class BookListAdapter(private var bookList: ArrayList<Book>) : RecyclerView.Adapter<BookListAdapter.ListHolder>() {
//    public val planetList: ArrayList<Planet>
    private var pagenum: Int
    init{
        //planetList = ArrayList<Planet>()
        pagenum = 0;
    }
    class ListHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val nameItemView: TextView
        val authorItemView: TextView
        val descItemView: TextView
        val imageItemView: ImageView
        init{
            nameItemView = itemView.findViewById(R.id.title)
            authorItemView = itemView.findViewById(R.id.author)
            descItemView = itemView.findViewById(R.id.descript)
            imageItemView = itemView.findViewById(R.id.imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListHolder {
        val ctxt = parent.context
        val inflater = LayoutInflater.from(ctxt)
        val listView = inflater.inflate(R.layout.booklayout, parent, false)
        return ListHolder(listView)
    }

    override fun onBindViewHolder(holder: ListHolder, position: Int) {
        val m = bookList.get(position)
        holder.nameItemView.text = m.name
        holder.authorItemView.text = m.pub
        holder.descItemView.text = m.desc
        Glide.with(holder.imageItemView)
            .load(m.img)
            .centerInside()
            .into(holder.imageItemView)
    //        holder.imageItemView.setImageDrawable()
    }

    override fun getItemCount(): Int {
        return bookList.size
    }

}