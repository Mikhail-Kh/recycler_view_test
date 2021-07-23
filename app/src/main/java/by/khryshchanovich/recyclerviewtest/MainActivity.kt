package by.khryshchanovich.recyclerviewtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.khryshchanovich.recyclerviewtest.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var recycler: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeRecyclerView()
        showImages()
    }

    private fun initializeRecyclerView() {
        recycler = binding.recyclerView
        recycler.layoutManager = GridLayoutManager(
            this,
            10,
            GridLayoutManager.HORIZONTAL,
            false
        )
    }

    private fun showImages() {
        var clickNumberAddButton = 0
        val imageListAddButton = ArrayList<String>()
        val imageListReloadAllButton = ArrayList<String>()

        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.add -> {
                    clickNumberAddButton++
                    imageListAddButton.add(BASE_URL.plus(clickNumberAddButton))
                    recycler.adapter = Adapter(imageListAddButton)
                    true
                }
                R.id.reloadAll -> {
                    imageListAddButton.clear()
                    for (i in 1..IMAGE_NUMBER) {
                        imageListReloadAllButton.add(BASE_URL.plus(i))
                    }
                    recycler.adapter = Adapter(imageListReloadAllButton)
                    true
                }
                else -> false
            }
        }
    }

    companion object {
        private const val BASE_URL = "https://picsum.photos/200/200/?random"
        private const val IMAGE_NUMBER = 140
    }
}