package com.example.modern_practices.Grid

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.modern_practices.Constant.VIEW_TYPE_ITEM
import com.example.modern_practices.Constant.VIEW_TYPE_LOADING
import com.example.modern_practices.LoadMoreItem
import com.example.modern_practices.OnLoadMoreListener
import com.example.modern_practices.databinding.ActivityGridRecyclerViewBinding
import kotlin.random.Random

class GridRecyclerView : AppCompatActivity() {

    private lateinit var binding: ActivityGridRecyclerViewBinding
    private lateinit var itemsCells: ArrayList<Int?>
    private lateinit var loadMoreItemsCells: ArrayList<Int?>
    private lateinit var scrollListener: LoadMoreItem
    private lateinit var adapterGrid: GridItemsAdapter
    private lateinit var layoutManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGridRecyclerViewBinding.inflate(layoutInflater)
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
            itemsCells.add(color)
        }
    }

    private fun setAdapter() {
        adapterGrid = GridItemsAdapter(itemsCells)
        adapterGrid.notifyDataSetChanged()
        binding.itemsGridRv.adapter = adapterGrid
    }

    private fun setLayoutManager() {
        layoutManager = GridLayoutManager(this, 3)
        binding.itemsGridRv.layoutManager = layoutManager
        binding.itemsGridRv.setHasFixedSize(true)
        binding.itemsGridRv.adapter = adapterGrid

        (layoutManager as GridLayoutManager).spanSizeLookup =
            object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return when (adapterGrid.getItemViewType(position)) {
                        VIEW_TYPE_ITEM -> 1
                        VIEW_TYPE_LOADING -> 3
                        else -> -1
                    }
                }
            }
    }

    private fun setScrollListener() {
        scrollListener = LoadMoreItem(layoutManager as GridLayoutManager)
        scrollListener.setOnLoadMoreListener(object : OnLoadMoreListener {
            override fun OnLoadMore() {
                loadMoreData()
            }
        })

        binding.itemsGridRv.addOnScrollListener(scrollListener)
    }

    private fun loadMoreData() {
        adapterGrid.addLoadingView()
        loadMoreItemsCells = ArrayList()
        val start = adapterGrid.itemCount
        val end = start + 16

        Handler().postDelayed({
            for (i in start..end) {
                val rand = Random
                val color = Color.argb(255, rand.nextInt(256), rand.nextInt(256), rand.nextInt(256))
                loadMoreItemsCells.add(color)
            }

            adapterGrid.removeLoadingView()
            adapterGrid.addData(loadMoreItemsCells)
            scrollListener.setLoaded()
            binding.itemsGridRv.post {
                adapterGrid.notifyDataSetChanged()
            }
        }, 3000)
    }
}
