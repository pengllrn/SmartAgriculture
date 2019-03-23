package com.base.widgets

import android.content.Context
import android.util.AttributeSet
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import com.base.R

/**
 * Author：pengllrn
 * Time: 2019/3/22 23:29
 */
class BottomNavBar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : BottomNavigationBar(context, attrs, defStyleAttr) {

    init {
        val discoveryItem = BottomNavigationItem(
            R.drawable.btn_nav_discovery_press,resources.getString(R.string.nav_bar_discovery))
            .setInactiveIconResource(R.drawable.btn_nav_discovery_normal)
            .setActiveColorResource(R.color.common_green)
            .setInActiveColorResource(R.color.text_normal)

        val exploreItem = BottomNavigationItem(
            R.drawable.btn_nav_explore_press,resources.getString(R.string.nav_bar_explore))
            .setInactiveIconResource(R.drawable.btn_nav_explore_normal)
            .setActiveColorResource(R.color.common_green)
            .setInActiveColorResource(R.color.text_normal)

        val collectionItem = BottomNavigationItem(
            R.drawable.btn_nav_collection_press,resources.getString(R.string.nav_bar_collection))
            .setInactiveIconResource(R.drawable.btn_nav_collection_normal)
            .setActiveColorResource(R.color.common_green)
            .setInActiveColorResource(R.color.text_normal)

        val userItem = BottomNavigationItem(
            R.drawable.btn_nav_user_press,resources.getString(R.string.nav_bar_user))
            .setInactiveIconResource(R.drawable.btn_nav_user_normal)
            .setActiveColorResource(R.color.common_green)
            .setInActiveColorResource(R.color.text_normal)

        setMode(BottomNavigationBar.MODE_FIXED)//模式
        setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC)//背景风格
        setBarBackgroundColor(R.color.common_white)//背景颜色

        addItem(discoveryItem)
            .addItem(exploreItem)
            .addItem(collectionItem)
            .addItem(userItem)
            .setFirstSelectedPosition(0)//默认选择
            .initialise()
    }
}