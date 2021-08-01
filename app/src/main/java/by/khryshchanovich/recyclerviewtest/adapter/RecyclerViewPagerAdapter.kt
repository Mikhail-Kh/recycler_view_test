package by.khryshchanovich.recyclerviewtest.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import java.util.*

class RecyclerViewPagerAdapter(private val recyclerViewList: ArrayList<RecyclerView>) :
    PagerAdapter() {
    override fun getCount(): Int {
        return if (recyclerViewList.isEmpty()) 0 else recyclerViewList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(recyclerViewList[position])
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        container.addView(recyclerViewList[position])
        return recyclerViewList[position]
    }
}