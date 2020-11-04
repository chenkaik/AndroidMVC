package com.android.mvvm.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.android.lib.Logger.e
import com.android.lib.banner.RecyclerViewBannerBaseView
import com.android.lib.util.InputTextHelper
import com.android.mvvm.MainActivity
import com.android.mvvm.R
import com.android.mvvm.activity.HttpActivity
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * date: 2020/9/21
 * desc: 首页
 */
class HomeFragment : BaseFragment() {

    private var activity: MainActivity? = null
    private lateinit var inputTextHelper: InputTextHelper

    companion object {
        private const val TAG = "HomeFragment"
        fun newInstance() = HomeFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val viewRes: MutableList<String> = ArrayList()
        viewRes.add("https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/676ddccc418842904f82ccdc66bbaa34.jpg?w=2452&h=920")
        viewRes.add("https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/cf6ba4d372b80e939104cf369f14139a.jpg?thumb=1&w=1226&h=460&f=webp&q=90")
        viewRes.add("https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/fe25c546580ff179bf6e60fb54d7afd4.jpg?thumb=1&w=1226&h=460&f=webp&q=90")
        banner.initBannerImageView(viewRes,
            object : RecyclerViewBannerBaseView.OnBannerItemClickListener {
                override fun onItemClick(position: Int) {
                    Toast.makeText(getMyActivity(), position.toString(), Toast.LENGTH_SHORT).show()
                }
            })
        banner.setPlaying(true)
//        startActivity<MainActivity>(this)
//        if ("hello" beginsWith "he"){
//
//        }
//        "t".showToast(this)
//        R.string.app_name.showToast(this)
//        val dialog = CommonDialog(getMyActivity())
        inputTextHelper = InputTextHelper(button1)
        inputTextHelper.addViews(et_register_phone, et_register_code)
        button1.setOnClickListener {
            //            dialog.showProgress("hh")
//            dialog.showAlertDialog("提示","这是内容", View.OnClickListener {
//                "确定".showToast()
//            }, View.OnClickListener {
//                "取消".showToast()
//            })
//            dialog.showOneAlertDialog("提示1","这是内容1",View.OnClickListener {
//                "确定了".showToast()
//            }


        }
        http.setOnClickListener {
//            val intent = Intent(getMyActivity(), HttpActivity::class.java)
//            ActivityCollector.startPage(getMyActivity(), intent, true)
            HttpActivity.actionStart(getMyActivity(), true)
//            startActivity<HttpActivity>(getMyActivity(), true)
//            startActivity<HttpActivity>(getMyActivity(), true, 100)
//            startActivity<HttpActivity>(getMyActivity(), true) {
//                putExtra("test", "hello")
//            }
//            startActivity<HttpActivity>(getMyActivity(), true, 100) {
//                putExtra("test", "hello")
//            }
        }

        button3.setOnClickListener {

        }

    }

    override fun onResume() {
        super.onResume()
        e(TAG, "onResume: ")
    }

    override fun onDestroy() {
        inputTextHelper.removeViews()
        super.onDestroy()
    }

    private fun getMyActivity() = activity ?: getActivity() as MainActivity


}