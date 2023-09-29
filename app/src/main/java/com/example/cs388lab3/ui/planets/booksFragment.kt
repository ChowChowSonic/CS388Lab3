package com.example.cs388lab3.ui.planets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.cs388lab3.R
import com.example.cs388lab3.databinding.FragmentPlanetsBinding
import org.json.JSONArray
import org.json.JSONObject

class booksFragment : Fragment() {
    private val bookList = ArrayList<Book>()
    private var pagenum: Int = 0
    private var _binding: FragmentPlanetsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val bookViewModel = ViewModelProvider(this).get(BookViewModel::class.java)
        _binding = FragmentPlanetsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val recycler: RecyclerView = root.findViewById<RecyclerView>(R.id.list)
        if(bookList.size == 0) makeAPIReq(root, 0)
        recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = bookList.size
                val layoutManager = LinearLayoutManager::class.java.cast(recyclerView.layoutManager)
                val lastVisible = layoutManager.findLastVisibleItemPosition()
                val endHasBeenReached = lastVisible + 5 >= totalItemCount
                if (totalItemCount == 0 || endHasBeenReached) {
                    //you have reached to the bottom of your recycler view
                    makeAPIReq(root, pagenum++)
                }
            }
        })

        recycler.adapter =  BookListAdapter(bookList)
        recycler.layoutManager = LinearLayoutManager(root.context)

        return root
    }

    fun makeAPIReq(group:View, page:Int){
        // creating a variable for our request queue and initializing it.
        val queue: RequestQueue = Volley.newRequestQueue(group.context)
        // creating a variable for request and initializing it with json object request
        val request = JsonObjectRequest(
            Request.Method.GET,
            "https://api.nytimes.com/svc/books/v3/lists/current/hardcover-fiction.json?api-key=tgNZV8KWQtEFcsAuM1F1sTaa3YWPr4FA",
            null,
            { response ->
                try {
                    println(response)
                    val bookarray: JSONArray = response.getJSONObject("results").getJSONArray("books")

                    for(x in 0 .. bookarray.length()-1) {
                        var p:JSONObject = bookarray.getJSONObject(x)
                        bookList.add(
                            Book(
                                p.getString("title"),
                                p.getString("publisher"),
                                p.getString("description"),
                                p.getString("book_image")
                                )
                        )
                    }
                    group.findViewById<RecyclerView>(R.id.list).adapter?.notifyItemRangeChanged(0, bookList.size)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }, {error -> error.printStackTrace()})
        queue.add(request)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}