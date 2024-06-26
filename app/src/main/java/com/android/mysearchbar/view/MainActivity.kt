package com.android.mysearchbar.view

import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.mysearchbar.R
import com.android.mysearchbar.view.adapter.Item
import com.android.mysearchbar.view.adapter.ItemAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var itemAdapter: ItemAdapter
    private lateinit var itemList: List<Item>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createItemList()
        setupRecyclerview()
        setupSearchView()
    }

    // Setup Recycler view
    private fun setupRecyclerview() {
        itemAdapter = ItemAdapter(itemList)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = itemAdapter
        recyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL))
    }

    private fun setupSearchView() {
        val searchView: SearchView = findViewById(R.id.searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                itemAdapter.filter(newText.orEmpty())
                return true
            }
        })

        // Handle search cancellation
        searchView.setOnCloseListener {
            itemAdapter.filter("")
            false
        }
    }

    // Create items list
    private fun createItemList() {
        itemList = listOf(
            Item("Apple"),
            Item("Banana"),
            Item("Cherry"),
            Item("Date"),
            Item("Elderberry"),
            Item("Fig"),
            Item("Grape"),
            Item("Honeydew"),
            Item("Kiwi"),
            Item("Lemon")
        )
    }

}