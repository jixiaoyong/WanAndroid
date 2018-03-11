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

class DiscoverTreeAdapter(private val context: Context,
                          private val data:ArrayList<DiscoverTreeBean.DataBean>)
    : BaseExpandableListAdapter() {

    override fun getGroup(groupPosition: Int): Any {

        return this.data[groupPosition]
    }

    //child可点击
    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?): View {

        val name = this.data[groupPosition].name

        if (convertView == null) {

            var view = LayoutInflater.from(this.context).inflate(R.layout.item_discover_tree_group, null)

            view.title.text = name

            return view
        }

        convertView.title.text = name

        return convertView
    }

    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View {

        val name = data[groupPosition].children[childPosition].name

        val id = data[groupPosition].children[childPosition].id

        if (convertView == null) {

            var view = LayoutInflater.from(this.context).inflate(R.layout.item_discover_tree_child, null)

            view.title.text = name

            setClickListener(view,id)

            return view
        }

        convertView.title.text = name

        setClickListener(convertView, id)

        return convertView
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return data[groupPosition].children.size
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        return data[groupPosition].children[childPosition]
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun getGroupCount(): Int {
        return data.size
    }

    private fun setClickListener(view: View,id:Int) {

        val url = "http://www.wanandroid.com/article/list/0?cid=$id"

        view.setOnClickListener {

            SuperUtil.startActivity(context,ContentActivity::class.java,url)

        }
    }


}
