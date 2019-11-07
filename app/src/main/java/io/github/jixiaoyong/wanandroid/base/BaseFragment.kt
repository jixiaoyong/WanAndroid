package io.github.jixiaoyong.wanandroid.base

import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2019-11-05
 * description: todo
 */
open class BaseFragment : Fragment(), CoroutineScope by MainScope()