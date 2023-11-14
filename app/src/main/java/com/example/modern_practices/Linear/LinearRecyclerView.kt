package com.example.modern_practices.Linear

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.modern_practices.LoadMoreItem
import com.example.modern_practices.OnLoadMoreListener
import com.example.modern_practices.databinding.ActivityLinearRecyclerViewBinding

class LinearRecyclerView : AppCompatActivity() {

    private lateinit var binding: ActivityLinearRecyclerViewBinding
    private lateinit var itemsCells: ArrayList<String?>
    private lateinit var loadMoreItemCells: ArrayList<String?>
    private lateinit var adapterLinear: LinearItemsAdapter
    private lateinit var scrollListener: LoadMoreItem
    private lateinit var mLayoutManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLinearRecyclerViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //** Set the data for our ArrayList
        setItemsData()

        //** Set the adapterLinear of the RecyclerView
        setAdapter()

        //** Set the Layout Manager of the RecyclerView
        setLayoutManager()

        //** Set the scrollListerner of the RecyclerView
        setScrollListener()
    }

    private fun setItemsData() {
        itemsCells = ArrayList()
        for (i in 0..40) {
            itemsCells.add("Item $i")
        }
    }

    private fun setAdapter() {
        adapterLinear = LinearItemsAdapter(itemsCells)
        adapterLinear.notifyDataSetChanged()
        binding.itemsLinearRv.adapter = adapterLinear
    }

    private fun setLayoutManager() {
        mLayoutManager = LinearLayoutManager(this)
        binding.itemsLinearRv.layoutManager = mLayoutManager
        binding.itemsLinearRv.setHasFixedSize(true)
    }

    private fun setScrollListener() {
        mLayoutManager = LinearLayoutManager(this)
        scrollListener = LoadMoreItem(mLayoutManager as LinearLayoutManager)
        scrollListener.setOnLoadMoreListener(object : OnLoadMoreListener {
            override fun OnLoadMore() {
                loadMoreData()
            }
        })
        binding.itemsLinearRv.addOnScrollListener(scrollListener)
    }

    private fun loadMoreData() {
        adapterLinear.addLoadingView()
        loadMoreItemCells = ArrayList()

        // Number of current items of the main ArrayList
        val start = adapterLinear.itemCount
        val end = start + 16

        //Use Handler if the items are loading too fast.
        //If you remove it, the data will load so fast that you can't even see the LoadingView
        Handler().postDelayed({
            for (i in start..end) {
                loadMoreItemCells.add("Item $i")
            }

            adapterLinear.removeLoadingView()
            adapterLinear.addData(loadMoreItemCells)
            scrollListener.setLoaded()

            binding.itemsLinearRv.post {
                adapterLinear.notifyDataSetChanged()
            }

        }, 3000)
    }
}
