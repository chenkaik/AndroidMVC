package com.android.mvvm.activity

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.lib.adapter.BaseRecyclerViewAdapter
import com.android.lib.adapter.LoadMoreWrapperAdapter
import com.android.lib.listener.EndlessRecyclerOnScrollListener
import com.android.lib.util.kotlin.startActivity
import com.android.mvvm.R
import com.android.mvvm.adapter.BaseRecyclerAdapter
import com.android.mvvm.databinding.ActivityBaseRecyclerViewBinding
import com.android.mvvm.databinding.CommonHeadLayoutBinding
import com.android.mvvm.util.showToast
import java.util.*
import kotlin.collections.ArrayList

class BaseRecyclerViewActivity : BaseActivity(), BaseRecyclerViewAdapter.OnItemClickListener,
    BaseRecyclerViewAdapter.OnItemLongClickListener, BaseRecyclerViewAdapter.OnViewClickListener,
    BaseRecyclerViewAdapter.OnMenuClickLister {

    private lateinit var activityBaseRecyclerViewBinding: ActivityBaseRecyclerViewBinding
    private lateinit var commonHeadLayoutBinding: CommonHeadLayoutBinding
    private val list: MutableList<String> = ArrayList()
    private lateinit var loadMoreWrapperAdapter: LoadMoreWrapperAdapter
    private lateinit var baseRecyclerAdapter: BaseRecyclerAdapter
    private val total = 100

    companion object {
        private const val TAG = "BaseRecyclerViewActivity"
        fun actionStart(activity: FragmentActivity, isPutStack: Boolean) {
            startActivity<BaseRecyclerViewActivity>(activity, isPutStack)
        }
    }

    override fun getLayoutView(): View {
        activityBaseRecyclerViewBinding = ActivityBaseRecyclerViewBinding.inflate(layoutInflater)
        commonHeadLayoutBinding = activityBaseRecyclerViewBinding.commonHead
        return activityBaseRecyclerViewBinding.root
    }

    override fun initView() {
        commonHeadLayoutBinding.navigationBar.setTitle("RecyclerView的基本使用")
        activityBaseRecyclerViewBinding.baseRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    @SuppressLint("InflateParams")
    override fun initData() {
        getData()
        baseRecyclerAdapter = BaseRecyclerAdapter(this, list, this, this)
        baseRecyclerAdapter.setOnItemClickListener(this)
        baseRecyclerAdapter.setOnItemLongClickListener(this)
        loadMoreWrapperAdapter = LoadMoreWrapperAdapter(baseRecyclerAdapter)
        val headView = LayoutInflater.from(this).inflate(R.layout.add_head_layout, null)
        headView.findViewById<Button>(R.id.btn_test1).setOnClickListener {
            "测试1".showToast()
        }
        headView.findViewById<Button>(R.id.btn_test2).setOnClickListener {
            "测试2".showToast()
        }
        val params = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        headView.layoutParams = params
        loadMoreWrapperAdapter.addHeaderView(headView)
//        activityBaseRecyclerViewBinding.baseRecyclerView.addHeaderView(headView)
        activityBaseRecyclerViewBinding.baseRecyclerView.adapter = loadMoreWrapperAdapter
        activityBaseRecyclerViewBinding.baseRecyclerView.addOnScrollListener(object :
            EndlessRecyclerOnScrollListener() {
            override fun onLoadMore() {
                if (list.size < total) {
                    loadMoreWrapperAdapter.setLoadState(loadMoreWrapperAdapter.loading)
                    Timer().schedule(object : TimerTask() {
                        override fun run() {
                            runOnUiThread {
                                getData()
                                loadMoreWrapperAdapter.setLoadState(loadMoreWrapperAdapter.loadingComplete)
                            }
                        }
                    }, 1000)
                } else {
                    loadMoreWrapperAdapter.setLoadState(loadMoreWrapperAdapter.loadingEnd)
                }
            }
        })
        activityBaseRecyclerViewBinding.baseRefreshLayout.setColorSchemeColors(
            ContextCompat.getColor(
                this,
                R.color.colorButtonPressed
            )
        )
        activityBaseRecyclerViewBinding.baseRefreshLayout.setOnRefreshListener {
            list.clear()
            getData()
            loadMoreWrapperAdapter.notifyDataSetChanged()
            activityBaseRecyclerViewBinding.baseRefreshLayout.postDelayed({
                activityBaseRecyclerViewBinding.baseRefreshLayout.isRefreshing = false
            }, 1000)
        }
    }

    private fun getData() {
        for (t in 0 until 20) {
            list.add("测试数据$t")
        }
    }

    override fun onItemClick(adapter: RecyclerView.Adapter<*>, v: View, position: Int) {
        "点击了$position".showToast()
    }

    override fun onItemLongClick(adapter: RecyclerView.Adapter<*>, v: View, position: Int) {
        "长按了$position".showToast()
    }

    override fun onViewClick(position: Int, type: Int) {
        "子View点击了$position".showToast()
    }

    override fun onMenuClick(view: View?, position: Int, type: Int) {
        when (type) {
            1 -> {
                "侧滑删除$position".showToast()
            }
            2 -> {
                "侧滑添加$position".showToast()
            }
        }
//        baseRecyclerView.closeMenu()
    }

}
