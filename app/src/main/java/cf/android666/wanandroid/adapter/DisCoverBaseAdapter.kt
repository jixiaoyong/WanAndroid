package cf.android666.wanandroid.adapter

import android.content.Context
import android.widget.BaseExpandableListAdapter

/**
 * Created by jixiaoyong on 2018/3/15.
 * email:jixiaoyong1995@gmail.com
 */
abstract class DisCoverBaseAdapter<T>(private val context: Context,
                                      private val data: ArrayList<T>
                                     )
    : BaseExpandableListAdapter() {

    override fun getGroup(groupPosition: Int): T {

        return (data[groupPosition])
    }

    //child可点击
    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }

    override fun hasStableIds(): Boolean {
        return false
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

}
