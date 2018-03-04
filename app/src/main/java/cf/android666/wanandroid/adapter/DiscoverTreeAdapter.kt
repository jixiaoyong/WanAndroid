package cf.android666.wanandroid.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import cf.android666.wanandroid.R
import cf.android666.wanandroid.activity.ContentActivity
import cf.android666.wanandroid.bean.DiscoverTreeBean
import cf.android666.wanandroid.utils.SuperUtil
import kotlinx.android.synthetic.main.item_discover_tree_group.view.*

class DiscoverTreeAdapter(context: Context,data:ArrayList<DiscoverTreeBean.DataBean>)
    : BaseExpandableListAdapter() {

    private var mContext :Context? = null

    private var mData : ArrayList<DiscoverTreeBean.DataBean> = arrayListOf()

    init {

        mContext = context

        mData = data

    }

    override fun getGroup(groupPosition: Int): Any {

        return mData[groupPosition]
    }

    //child可点击
    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?): View {

        val name = mData[groupPosition].name

        if (convertView == null) {

            var view = LayoutInflater.from(mContext).inflate(R.layout.item_discover_tree_group, null)

            view.title.text = name

//            setClickListener(view)

            return view
        }

        convertView!!.title.text = name

//        setClickListener(convertView)

        return convertView
    }

    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View {

        val name = mData[groupPosition].children[childPosition].name

        val id = mData[groupPosition].children[childPosition].id

        if (convertView == null) {

            var view = LayoutInflater.from(mContext).inflate(R.layout.item_discover_tree_child, null)

            view.title.text = name

            setClickListener(view,id)

            return view
        }

        convertView!!.title.text = name

        setClickListener(convertView, id)

        return convertView
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return mData[groupPosition].children.size
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        return mData[groupPosition].children[childPosition]
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun getGroupCount(): Int {
        return mData.size
    }

    private fun setClickListener(view: View,id:Int) {

        val url = "http://www.wanandroid.com/article/list/0?cid=$id"

        view.setOnClickListener {

            SuperUtil.startActivity(mContext!!,ContentActivity::class.java,url)

        }
    }


}
