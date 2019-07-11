package com.lxm.wanandroid

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.lxm.module_library.statusbar.StatusBarUtil
import com.lxm.wanandroid.ui.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_content.*


const val PHOTO_URL = "http://cdn.duitang.com/uploads/blog/201404/22/20140422142715_8GtUk.thumb.600_0.jpeg"

class MainActivity : AppCompatActivity() {

    private val mTitles by lazy {
        mutableListOf("主页", "知识体系", "导航", "项目", "福利")
    }
    private val mFragments: MutableList<Fragment> by lazy {
        mutableListOf<Fragment>()
    }

    private val mAdapter: MyPagerAdapter by lazy {
        MyPagerAdapter(supportFragmentManager)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        StatusBarUtil.setColor(this, resources.getColor(R.color.colorTheme), 0)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        iv_photo.setOnClickListener {
            drawerLayout.openDrawer(Gravity.START)
        }
        val imageView = navigationView.getHeaderView(0).findViewById<ImageView>(R.id.ivHeadView)
        val textView = navigationView.getHeaderView(0).findViewById<TextView>(R.id.tvUserName)
        showPhoto(imageView)
        showPhoto(iv_photo)
        navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {

                R.id.colletion -> {
                }
                R.id.about -> {

                }
                else -> {

                }
            }
            drawerLayout.closeDrawer(Gravity.START)
            true
        }


        mFragments.let {
            it.add(ArticleFragment.getInstance())
            it.add(TreeFragment.getInstance())
            it.add(NavigationFragment.getInstance())
            it.add(ProjectFragment.getInstance())
            it.add(WelfareFragment.getInstance())
        }

        mAdapter.setFragments(mFragments)
        mAdapter.setTitles(mTitles)
        view_pager.adapter = mAdapter
        view_pager.offscreenPageLimit = 5
        tablayout.setViewPager(view_pager)
    }

    private fun showPhoto(imageView: ImageView) {
        Glide.with(this@MainActivity)
            .load(PHOTO_URL)
            .transition(DrawableTransitionOptions.withCrossFade(500))
            .transform(CircleCrop())
            .into(iv_photo)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> {

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (drawerLayout.isDrawerOpen(Gravity.START)) {
                drawerLayout.closeDrawer(Gravity.START)
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }
}

class MyPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    var fragmentList: MutableList<Fragment> = mutableListOf()
    var titleList: MutableList<String> = mutableListOf()

    override fun getCount(): Int {
        return fragmentList.size
    }

    override fun getItem(p0: Int): Fragment {
        return fragmentList[p0]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titleList[position]
    }

    fun setFragments(fragment: MutableList<Fragment>) {
        fragmentList = fragment;
    }

    fun setTitles(mTitles: MutableList<String>) {
        titleList = mTitles
    }
}

