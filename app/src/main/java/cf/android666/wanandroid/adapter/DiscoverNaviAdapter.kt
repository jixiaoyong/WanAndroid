package cf.android666.wanandroid.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cf.android666.wanandroid.R
import cf.android666.wanandroid.activity.ContentActivity
import cf.android666.wanandroid.bean.DiscoverNaviBean
import cf.android666.wanandroid.utils.SuperUtil
import kotlinx.android.synthetic.main.item_discover_tree_group.view.*

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2019-11-02
 * description: todo
 */
class DiscoverNaviAdapter(private val context: Context,
                          private val  data:ArrayList<DiscoverNaviBean.DataBean>)
    : DisCoverBaseAdapter<DiscoverNaviBean.DataBean>(context,data) {

    private var groupLayout: Int = R.layout.item_discover_tree_group
    private var childLayout: Int = R.layout.item_discover_tree_child


    override fun getChildrenCount(groupPosition: Int): Int {
        return data[groupPosition].articles.size
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        return data[groupPosition].articles[childPosition]
    }

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?): View {

        val name = data[groupPosition].name

        if (convertView == null) {

            var view = LayoutInflater.from(context).inflate(groupLayout, null)

            view.title.text = name

            return view
        }

        convertView.title.text = name

        return convertView
    }

    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View {

        val name =  data[groupPosition].articles[childPosition].title

        val url =  data[groupPosition].articles[childPosition].link

        if (convertView == null) {

            var view = LayoutInflater.from(context).inflate(childLayout, null)

            view.title.text = name

            setClickListener(view, url)

            return view
        }

        convertView.title.text = name

        setClickListener(convertView, url)

        return convertView
    }

    private fun setClickListener(view: View, url: String) {

        view.setOnClickListener {

            SuperUtil.startActivity(context, ContentActivity::class.java, url)

        }
    }

}
