package cf.android666.wanandroid.`interface`

import cf.android666.wanandroid.utils.MessageEvent
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * Created by jixiaoyong on 2018/3/14.
 * email:jixiaoyong1995@gmail.com
 */
interface RefreshUiInterface{

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    open fun handlerMessage(eventMsg:MessageEvent){
        refreshUi()
    }

    fun refreshUi()
}