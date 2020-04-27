package com.example.aotedan.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.aotedan.R;
import com.example.aotedan.Entity.GasEntity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class GasListAdapter extends RecyclerView.Adapter<GasListAdapter.myViewHodler> {
    private Context context;
    private ArrayList<GasEntity> gasEntityArrayList;

    //创建构造函数
    public GasListAdapter(Context context, ArrayList<GasEntity> gasEntityArrayList) {
        //将传递过来的数据，赋值给本地变量
        this.context = context;//上下文
        this.gasEntityArrayList = gasEntityArrayList;//实体类数据ArrayList
    }

    /**
     * 创建viewhodler，相当于listview中getview中的创建view和viewhodler
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public myViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        //创建自定义布局
        View itemView = View.inflate(context, R.layout.list_item_gas, null);
        return new myViewHodler(itemView);
    }

    /**
     * 绑定数据，数据与view绑定
     *
     * @param holder
     * @param position
     */
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(myViewHodler holder, int position) {
        //根据点击位置绑定数据
        GasEntity data = gasEntityArrayList.get(position);
        holder.gas_ch4.setText("甲烷：" + data.ch4 + "%" + " " +"一氧化碳："+ data.co + "ppm" +  " " +"氧气"+ data.o2 + "%" + " " + "温度：" +data.temperature+ "℃" +  " " +"湿度：" + data.humidity + "%");
        holder.gas_staff_name.setText("姓名："+ data.staff_name);
        holder.gas_group_name.setText("部门：" + data.group_name);
        String time = formatDate(data.createtime);
        holder.gas_create_time.setText("时间：" + time);
        holder.gas_position.setText("地点：" +data.temppositionname);
    }
    // 时间戳转时间
    public String formatDate(String time){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd :HH:mm:ss");
        String formatTime = sdf.format(new Date(Long.parseLong(String.valueOf(time))));//时间戳转换成时间
        return formatTime;
    }
    /**
     * 得到总条数
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return gasEntityArrayList.size();
    }

    //自定义viewhodler
    class myViewHodler extends RecyclerView.ViewHolder {
        private TextView gas_ch4;
        private TextView gas_staff_name;
        private TextView gas_group_name;
        private TextView gas_create_time;
        private TextView gas_position;

        public myViewHodler(View itemView) {
            super(itemView);
             gas_ch4 = (TextView) itemView.findViewById(R.id.gas_content);
             gas_staff_name = (TextView) itemView.findViewById(R.id.gas_staff_name);
             gas_group_name = (TextView) itemView.findViewById(R.id.gas_group_name);
             gas_position = (TextView) itemView.findViewById(R.id.gas_position);
             gas_create_time = (TextView) itemView.findViewById(R.id.gas_create_time);
            //点击事件放在adapter中使用，也可以写个接口在activity中调用
            //方法一：在adapter中设置点击事件
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //可以选择直接在本位置直接写业务处理
                    //Toast.makeText(context,"点击了xxx",Toast.LENGTH_SHORT).show();
                    //此处回传点击监听事件
                    if(onItemClickListener!=null){
                        onItemClickListener.OnItemClick(v, gasEntityArrayList.get(getLayoutPosition()));
                    }
                }
            });

        }
    }

    /**
     * 设置item的监听事件的接口
     */
    public interface OnItemClickListener {
        /**
         * 接口中的点击每一项的实现方法，参数自己定义
         *  @param view 点击的item的视图
         * @param data 点击的item的数据
         */
        public void OnItemClick(View view, GasEntity data);
    }

    //需要外部访问，所以需要设置set方法，方便调用
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}