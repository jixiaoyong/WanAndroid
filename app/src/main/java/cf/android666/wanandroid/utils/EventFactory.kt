package cf.android666.wanandroid.utils


/**
 * Created by jixiaoyong on 2018/3/7.
 * email:jixiaoyong1995@gmail.com
 */
data class EventFactory(private val clazz: Any) {

    data class NightMode(var value: Boolean) : EventInterface

    data class LoginState(var value: Boolean,var username:String) : EventInterface

    data class CollectState(var value: Int) : EventInterface
}

interface EventInterface
