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
    private lateinit var imageAdapter: ImageAdapter
    private lateinit var recyclerViewPagerAdapter: RecyclerViewPagerAdapter
    private var clickNumberReloadButton = 0
    private var clickNumberAddButton = 0
    private var isReloadAllButtonPressed = false
    private var pageNumberAddButton = clickNumberAddButton.div(singlePageImageNumber)
    private val imageListAddButton = ArrayList<String>()
    private val imageListReloadAllButton = ArrayList<String>()
    private val recyclerViewListReloadAllButton = ArrayList<RecyclerView>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showImages()
    }

    private fun showImages() {
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.add -> {
                    initViewAddButton(imageListReloadAllButton, imageListAddButton)
                    true
                }
                R.id.reloadAll -> {
                    initViewReloadAllButton(imageListReloadAllButton)
                    true
                }
                else -> false
            }
        }
    }

    private fun initViewAddButton(
        imageListReloadAllButton: ArrayList<String>,
        imageListAddButton: ArrayList<String>
    ) {
        if (imageListReloadAllButton.isNotEmpty()) {
            val recyclerViewListAddButton = ArrayList<RecyclerView>()
            checkReloadAllButtonPress(recyclerViewListAddButton)
            recyclerViewListAddButton.addAll(recyclerViewListReloadAllButton)
            clickNumberAddButton++
            imageListAddButton.add(BASE_URL.plus(clickNumberAddButton))
            if (clickNumberAddButton % singlePageImageNumber == 1) {
                pageNumberAddButton++
            }
            for (i in 0 until pageNumberAddButton) {
                val fromIndex = i * singlePageImageNumber
                var toIndex = (i + 1) * singlePageImageNumber
                if (toIndex > clickNumberAddButton) {
                    toIndex = clickNumberAddButton
                }
                val imageSubListAddButton =
                    ArrayList(imageListAddButton.subList(fromIndex, toIndex))
                setRecyclerView(imageSubListAddButton, recyclerViewListAddButton)
            }
            setPagerAdapter(recyclerViewListAddButton)
        }
    }

    private fun checkReloadAllButtonPress(recyclerViewListAddButton: ArrayList<RecyclerView>) {
        if (isReloadAllButtonPressed) {
            imageListAddButton.clear()
            recyclerViewListAddButton.clear()
            clickNumberAddButton = 0
            pageNumberAddButton = 0
            isReloadAllButtonPressed = false
        }
    }

    private fun initViewReloadAllButton(imageListReloadAllButton: ArrayList<String>) {
        clickNumberReloadButton++
        if (clickNumberReloadButton != 1) {
            isReloadAllButtonPressed = true
        }
        imageListReloadAllButton.clear()
        setImageListReloadAllButton()
        var pageNumberReloadAllButton = imageListReloadAllButton.size.div(singlePageImageNumber)
        if (imageListReloadAllButton.size % singlePageImageNumber > 0) {
            pageNumberReloadAllButton++
        }
        recyclerViewListReloadAllButton.clear()
        for (i in 0 until pageNumberReloadAllButton) {
            val fromIndex = i * singlePageImageNumber
            var toIndex = (i + 1) * singlePageImageNumber
            if (toIndex > imageListReloadAllButton.size) {
                toIndex = imageListReloadAllButton.size
            }
            val imageSubList = ArrayList(imageListReloadAllButton.subList(fromIndex, toIndex))
            setRecyclerView(imageSubList, recyclerViewListReloadAllButton)
        }
        setPagerAdapter(recyclerViewListReloadAllButton)
    }

    private fun setImageListReloadAllButton() {
        for (i in 1..IMAGE_NUMBER) {
            imageListReloadAllButton.add(BASE_URL.plus(i))
        }
    }

    private fun setRecyclerView(
        imageSubList: ArrayList<String>,
        recyclerViewList: ArrayList<RecyclerView>
    ) {
        val recyclerView = RecyclerView(this).apply {
            layoutManager = GridLayoutManager(
                context,
                ROW_PAGE,
                GridLayoutManager.HORIZONTAL,
                false
            )
            imageAdapter = ImageAdapter(imageSubList)
            imageAdapter.notifyDataSetChanged()
            adapter = imageAdapter
        }
        recyclerViewList.add(recyclerView)
    }

    private fun setPagerAdapter(recyclerViewList: ArrayList<RecyclerView>) {
        recyclerViewPagerAdapter = RecyclerViewPagerAdapter(recyclerViewList)
        recyclerViewPagerAdapter.notifyDataSetChanged()
        binding.viewPager.adapter = recyclerViewPagerAdapter
    }

    companion object {
        private const val BASE_URL = "https://picsum.photos/200/200/?random"
        private const val IMAGE_NUMBER = 140
        private const val singlePageImageNumber = ROW_PAGE * COLUMN_PAGE
    }
}