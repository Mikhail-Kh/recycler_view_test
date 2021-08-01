package by.khryshchanovich.recyclerviewtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.*
import by.khryshchanovich.recyclerviewtest.adapter.ImageAdapter
import by.khryshchanovich.recyclerviewtest.adapter.RecyclerViewPagerAdapter
import by.khryshchanovich.recyclerviewtest.databinding.ActivityMainBinding
import by.khryshchanovich.recyclerviewtest.util.COLUMN_PAGE
import by.khryshchanovich.recyclerviewtest.util.ROW_PAGE
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showImages()
    }

    private fun showImages() {
        var clickNumberAddButton = 0
        val imageList = ArrayList<String>()
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.add -> {
                    clickNumberAddButton++
                    if (imageList.isNotEmpty()) {
                        imageList.add(BASE_URL.plus(clickNumberAddButton))
                        initView(imageList)
                    }
                    true
                }
                R.id.reloadAll -> {
                    imageList.clear()
                    for (i in 1..IMAGE_NUMBER) {
                        imageList.add(BASE_URL.plus(i))
                    }
                    initView(imageList)
                    true
                }
                else -> false
            }
        }
    }

    private fun initView(imageList: ArrayList<String>) {
        val singlePageImageNumber = ROW_PAGE * COLUMN_PAGE
        var pageNumber = imageList.size / singlePageImageNumber
        if (imageList.size % singlePageImageNumber > 0) {
            pageNumber++
        }
        val recyclerViewList = ArrayList<RecyclerView>()
        for (i in 0 until pageNumber) {
            val fromIndex = i * singlePageImageNumber
            var toIndex = (i + 1) * singlePageImageNumber
            if (toIndex > imageList.size) {
                toIndex = imageList.size
            }
            val imageSubList = ArrayList(imageList.subList(fromIndex, toIndex))
            val recyclerView = RecyclerView(this).apply {
                layoutManager = GridLayoutManager(
                    context,
                    10,
                    GridLayoutManager.HORIZONTAL,
                    false
                )
                adapter = ImageAdapter(imageSubList)
            }
            recyclerViewList.add(recyclerView)
        }
        binding.viewPager.adapter = RecyclerViewPagerAdapter(recyclerViewList)
    }

    companion object {
        private const val BASE_URL = "https://picsum.photos/200/200/?random"
        private const val IMAGE_NUMBER = 140
    }
}