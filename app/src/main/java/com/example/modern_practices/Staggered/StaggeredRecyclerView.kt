package com.example.modern_practices.Staggered

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.modern_practices.LoadMoreItem
import com.example.modern_practices.OnLoadMoreListener
import com.example.modern_practices.R
import com.example.modern_practices.databinding.ActivityStaggeredRecyclerViewBinding
import kotlin.random.Random

class StaggeredRecyclerView : AppCompatActivity() {

    private lateinit var binding: ActivityStaggeredRecyclerViewBinding
    private lateinit var itemsCells: ArrayList<StaggeredModel?>
    private lateinit var loadMoreItemsCells: ArrayList<StaggeredModel?>
    private lateinit var adapterStaggered: StaggeredItemsAdapter
    private lateinit var scrollListener: LoadMoreItem
    private lateinit var layoutManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStaggeredRecyclerViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setItemsData()
        setAdapter()
        setLayoutManager()
        setScrollListener()
    }

    private fun setItemsData() {
        itemsCells = ArrayList()
        for (i in 0..41) {
            val rand = Random
            val color = Color.argb(255, rand.nextInt(256), rand.nextInt(256), rand.nextInt(256))
            val aspectRatio = rand.nextFloat() * (1.5F - 0.5F) + 0.5F
            itemsCells.add(StaggeredModel(color, aspectRatio))
        }
    }

    private fun setAdapter() {
        adapterStaggered = StaggeredItemsAdapter(itemsCells)
        adapterStaggered.notifyDataSetChanged()
        binding.itemsStaggeredRv.adapter = adapterStaggered
    }

    private fun setLayoutManager() {
        layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.itemsStaggeredRv.layoutManager = layoutManager
        binding.itemsStaggeredRv.setHasFixedSize(true)
        binding.itemsStaggeredRv.adapter = adapterStaggered
    }

    private fun setScrollListener() {
        scrollListener = LoadMoreItem(layoutManager as StaggeredGridLayoutManager)
        scrollListener.setOnLoadMoreListener(object : OnLoadMoreListener {
            override fun OnLoadMore() {
                loadMoreData()
            }
        })
        binding.itemsStaggeredRv.addOnScrollListener(scrollListener)
    }

    private fun loadMoreData() {
        adapterStaggered.addLoadingView()
        loadMoreItemsCells = ArrayList()

        val start = adapterStaggered.itemCount
        val end = start + 16

        Handler().postDelayed({
            for (i in start..end) {
                val rand = Random
                val color = Color.argb(255, rand.nextInt(256), rand.nextInt(256), rand.nextInt(256))
                val aspectRatio = rand.nextFloat() * (1.5f - 0.5f) + 0.5f
                loadMoreItemsCells.add(StaggeredModel(color, aspectRatio))
            }
            adapterStaggered.removeLoadingView()
            adapterStaggered.addData(loadMoreItemsCells)
            scrollListener.setLoaded()
            binding.itemsStaggeredRv.post {
                adapterStaggered.notifyDataSetChanged()
            }
        }, 3000)
    }

}
