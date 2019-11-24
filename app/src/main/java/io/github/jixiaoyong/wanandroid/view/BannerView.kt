package io.github.jixiaoyong.wanandroid.view

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Handler
import android.os.Message
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import cf.android666.applibrary.Logger
import cf.android666.applibrary.view.BannerViewHelper
import io.github.jixiaoyong.wanandroid.R
import kotlinx.android.synthetic.main.view_banner.view.*


/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2019-11-18
 * description: todo
 */
class BannerView : RelativeLayout {

    private var indicatorType: Int = 0
    lateinit var viewPager: androidx.viewpager.widget.ViewPager
    private lateinit var indicatorLayout: FrameLayout

    @JvmOverloads
    constructor (context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
            : super(context, attrs, defStyleAttr) {
        initView(context, attrs, defStyleAttr)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    constructor (context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int)
            : super(context, attrs, defStyleAttr, defStyleRes) {
        initView(context, attrs, defStyleAttr, defStyleRes)
    }

    private fun initView(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int = 0) {
        val view = LayoutInflater.from(context).inflate(R.layout.view_banner, this, true)
        viewPager = view.viewPager
        indicatorLayout = view.bottomView
        val a = context.obtainStyledAttributes(attrs, R.styleable.BannerView, defStyleAttr, defStyleRes)
        indicatorType = a.getInt(R.styleable.BannerView_indicatorType, IndicatorType.POINT)
        a.recycle()
    }

    @SuppressLint("ClickableViewAccessibility")
    fun setViewsAndIndicator(fragmentMng: FragmentManager, fragments: List<Fragment>, indicatorValue: List<String>? = null) {
        Logger.d("setViewsAndIndicator:#add Fragment")
        val newFragmentList = fragments.toMutableList()
        val lastFragment = fragments.last()
        val firstFragment = fragments.first()
        val prefixFragment = BannerViewHelper.ImageViewFragment.getFragment(lastFragment.view)
        val suffixFragment = BannerViewHelper.ImageViewFragment.getFragment(firstFragment.view)
        newFragmentList.add(0, prefixFragment)
        newFragmentList.add(suffixFragment)

        viewPager.adapter = VpAdapter(fragmentMng, newFragmentList)
        viewPager.currentItem = 1
        viewPager.addOnPageChangeListener(object : androidx.viewpager.widget.ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                val fakeListSize = newFragmentList.size
                val fakeIndex = when (position) {
                    fakeListSize - 1 -> 1
                    0 -> fakeListSize - 2
                    else -> position
                }

                viewPager.setCurrentItem(fakeIndex, false)

                onIndicatorSelected(viewPager.currentItem - 1, indicatorValue)
            }

        })
        viewPager.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> viewPagerHandler.removeMessages(WHAT_CHANGE_VIEWPAGE)
                MotionEvent.ACTION_UP -> startChangeViewPagerDelayed(BANNER_DURATION_MILLIS)
            }
            false
        }
        setupIndicator(fragments.size, indicatorValue)
    }

    fun onIndicatorSelected(position: Int, indicatorValue: List<String>? = null) {
        if (indicatorType == IndicatorType.POINT) {
            val indicatorView = indicatorLayout.getChildAt(0) as LinearLayout
            for (i in 0 until indicatorView.childCount) {
                val imgView = indicatorView.getChildAt(i) as ImageView
                imgView.setBackgroundResource(if (i == position) R.drawable.ic_point_enable
                else R.drawable.ic_point_disable)
            }
        } else if (indicatorType == IndicatorType.TEXT) {
            val indicatorView = indicatorLayout.getChildAt(0) as TextView
            indicatorView.text = indicatorValue?.getOrNull(position) ?: ""
        }
    }


    private fun setupIndicator(size: Int, indicatorValue: List<String>? = null) {
        if (indicatorType == IndicatorType.POINT) {
            val indicatorView = LayoutInflater.from(context).inflate(
                    R.layout.layout_banner_indicator, indicatorLayout, false) as LinearLayout
            for (i in 0 until size) {
                val imageView = ImageView(context)
                imageView.setBackgroundResource(if (i == 0) R.drawable.ic_point_enable
                else R.drawable.ic_point_disable)
                indicatorView.addView(imageView)
                val lp = LinearLayout.LayoutParams(imageView.layoutParams)
                lp.setMargins(15, 15, 15, 15)
                imageView.layoutParams = lp
            }
            indicatorLayout.addView(indicatorView)
        } else if (indicatorType == IndicatorType.TEXT) {
            val textView = LayoutInflater.from(context)
                    .inflate(R.layout.view_banner_indicator_text, indicatorLayout, false)
                    as TextView
            textView.text = indicatorValue?.get(0)
            indicatorLayout.addView(textView)
        }
    }


    override fun onWindowVisibilityChanged(visibility: Int) {
        super.onWindowVisibilityChanged(visibility)
        if (visibility == View.VISIBLE) {
            startChangeViewPagerDelayed(BANNER_DURATION_MILLIS)
        } else if (visibility == View.INVISIBLE || visibility == View.GONE) {
            viewPagerHandler.removeCallbacksAndMessages(null)
        }
    }

    private fun startChangeViewPagerDelayed(delayTimeMillis: Long) {
        viewPagerHandler.sendEmptyMessageDelayed(WHAT_CHANGE_VIEWPAGE, delayTimeMillis)
    }


    private val viewPagerHandler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message?) {
            when (msg?.what) {
                WHAT_CHANGE_VIEWPAGE -> {
                    try {
                        viewPager.currentItem = viewPager.currentItem + 1
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    startChangeViewPagerDelayed(BANNER_DURATION_MILLIS)
                }
                else -> {

                }
            }
        }
    }

    companion object {
        const val WHAT_CHANGE_VIEWPAGE = 1
        const val BANNER_DURATION_MILLIS = 3000L

        object IndicatorType {
            const val POINT = 0
            const val TEXT = 1
            const val NONE = -1
        }
    }

    class VpAdapter(fragmentManager: FragmentManager, private val fragments: List<Fragment>)
        : androidx.fragment.app.FragmentPagerAdapter(fragmentManager) {


        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }

        override fun getCount(): Int {
            return fragments.size
        }

    }
}