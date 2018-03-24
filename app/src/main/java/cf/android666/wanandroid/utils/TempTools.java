package cf.android666.wanandroid.utils;


import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;

import java.util.List;

import cf.android666.wanandroid.adapter.BannerViewHolder;
import cf.android666.wanandroid.bean.IndexBannerBean;

/**
 * Created by jixiaoyong on 2018/3/4.
 * email:jixiaoyong1995@gmail.com
 */

public class TempTools {

    public static void setBanner(List<IndexBannerBean.DataBean> data, MZBannerView mMZBannerView){

        mMZBannerView.setPages(data, new MViewHolder());

        mMZBannerView.start();

    }

    static class MViewHolder implements MZHolderCreator<BannerViewHolder> {
        @Override
        public BannerViewHolder createViewHolder() {
            return new BannerViewHolder();
        }
    }

}
