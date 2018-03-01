package cf.android666.wanandroid.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cf.android666.wanandroid.R;

/**
 * Created by jixiaoyong on 2018/3/1.
 */

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private Context mContext;
    private List<String> mDatas;
    private LayoutInflater inflater;

    public MyRecyclerAdapter(Context context, List<String> data) {
        mContext = context;
        mDatas = data;
        inflater = LayoutInflater.from(context);
    }

    @Override

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.recycler_item, null);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.textView.setText(mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }
}

class MyViewHolder extends RecyclerView.ViewHolder{
    TextView textView;
    public MyViewHolder(View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.textView);
    }
}
