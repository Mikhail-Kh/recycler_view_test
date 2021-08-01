package by.khryshchanovich.recyclerviewtest.util

import by.khryshchanovich.recyclerviewtest.databinding.ItemImageBinding

private const val INTERVAL_IMAGES_PT = 2
private const val PT_MEASURE_UNIT = 1.0 / 72
const val ROW_PAGE = 10
const val COLUMN_PAGE = 7

fun setImageViewSize(binding: ItemImageBinding) {
    with(binding) {
        val densityDpi = root.context.resources.displayMetrics.densityDpi
        val widthPixels = root.context.resources.displayMetrics.widthPixels
        val heightPixels = root.context.resources.displayMetrics.heightPixels
        imageView.layoutParams.width = widthPixels
            .minus(COLUMN_PAGE * INTERVAL_IMAGES_PT * PT_MEASURE_UNIT * densityDpi)
            .div(COLUMN_PAGE).toInt()
        imageView.layoutParams.height = heightPixels
            .minus(ROW_PAGE * INTERVAL_IMAGES_PT * PT_MEASURE_UNIT * densityDpi)
            .div(ROW_PAGE).toInt()
        imageView.requestLayout()
    }
}