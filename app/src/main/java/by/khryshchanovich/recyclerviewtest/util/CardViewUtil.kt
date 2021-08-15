package by.khryshchanovich.recyclerviewtest.util

import android.util.TypedValue
import by.khryshchanovich.recyclerviewtest.databinding.ItemImageBinding

private const val INTERVAL_IMAGES_PT = 2
private const val PT_MEASURE_UNIT = 1.0 / 72
private const val ROUNDING_COEFFICIENT = 0.5
private const val MDPI = 160
private const val HDPI = 240
private const val XHDPI = 320
const val COLUMN_PAGE = 7
const val ROW_PAGE = 10

fun initCardViewSize(binding: ItemImageBinding) {
    calculateDensityDpi(binding)
    setCardViewSize(binding)
}

private fun calculateDensityDpi(binding: ItemImageBinding) {
    when (binding.root.context.resources.displayMetrics.densityDpi) {
        MDPI -> ROUNDING_COEFFICIENT
        HDPI -> ROUNDING_COEFFICIENT
        XHDPI -> ROUNDING_COEFFICIENT
    }
}

private fun setCardViewSize(binding: ItemImageBinding) {
    with(binding) {
        val resources = root.context.resources
        val densityDpi = resources.displayMetrics.densityDpi
        val widthPixels = resources.displayMetrics.widthPixels
        val heightPixels = resources.displayMetrics.heightPixels
        val heightStatusBar = resources.getDimensionPixelSize(
            resources.getIdentifier(
                "status_bar_height",
                "dimen",
                "android"
            )
        )
        val typedValue = TypedValue()
        var heightActionBar = 0
        if (root.context.theme.resolveAttribute(
                android.R.attr.actionBarSize,
                typedValue,
                true
            )
        ) {
            heightActionBar =
                TypedValue.complexToDimensionPixelSize(
                    typedValue.data,
                    resources.displayMetrics
                )
        }

        cardView.layoutParams.width = widthPixels
            .minus(COLUMN_PAGE * INTERVAL_IMAGES_PT * PT_MEASURE_UNIT * densityDpi)
            .div(COLUMN_PAGE).plus(ROUNDING_COEFFICIENT).toInt()
        cardView.layoutParams.height = heightPixels
            .minus(ROW_PAGE * INTERVAL_IMAGES_PT * PT_MEASURE_UNIT * densityDpi)
            .minus(heightStatusBar)
            .minus(heightActionBar)
            .div(ROW_PAGE).toInt()
        cardView.requestLayout()
    }
}