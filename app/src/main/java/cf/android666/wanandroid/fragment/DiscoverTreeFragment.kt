package cf.android666.wanandroid.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cf.android666.wanandroid.R
import cf.android666.wanandroid.adapter.MyRecyclerAdapter
import cf.android666.wanandroid.base.BaseFragment
import cf.android666.wanandroid.view.ContactsRecyclerview
import kotlinx.android.synthetic.main.fragment_discover_tree.view.*

/**
 * Created by jixiaoyong on 2018/2/25.
 */
class DiscoverTreeFragment() : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_discover_tree, container, false)

        val names = arrayOf("你nihao你", "爱好Joan", "的Niki", "Betty", "Linda", "Whitney", "Lily", "Fred", "Gary", "William", "Charles", "Michael", "Karl", "arbara", "Elizabeth", "Helen", "Katharine", "Lee", "Ann", "Diana", "Fiona")

        val data :ArrayList<String> = arrayListOf()

        names.indices.mapTo(data) { names[it] }

        view.recycler_view.adapter = MyRecyclerAdapter(context,data)

        view.recycler_view.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        view.recycler_view.addItemDecoration(ContactsRecyclerview.Decoration(data))


        return view
    }
}