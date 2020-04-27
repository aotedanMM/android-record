package com.example.aotedan.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.aotedan.Entity.StaffEntity;
import com.example.aotedan.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class StaffListAdapter extends RecyclerView.Adapter<StaffListAdapter.myViewHodler> {
    private Context context;
    private ArrayList<StaffEntity> staffEntityArrayList;

    //创建构造函数
    public StaffListAdapter(Context context, ArrayList<StaffEntity> staffEntityArrayList) {
        //将传递过来的数据，赋值给本地变量
        this.context = context;//上下文
        this.staffEntityArrayList = staffEntityArrayList;//实体类数据ArrayList
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
        View itemView = View.inflate(context, R.layout.list_item_staff, null);
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
        StaffEntity data = staffEntityArrayList.get(position);
        String inOreTime = formatDate(data.inOreTime); // 时间戳转时间
        String finalTime = formatDate(data.finalTime); // 时间戳转时间
        holder.staff_staff_name.setText("姓名：" + data.staffName);
        holder.staff_group_name.setText("部门：" + data.deptName);
        holder.staff_in_time.setText("入井时间：" + inOreTime);
        holder.staff_near_time.setText("最近定位时间：" + finalTime);
        holder.staff_time_long.setText("井下时长：" + data.timeLong);
    }
    // 时间戳转时间
    public String formatDate(String time){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd :HH:mm:ss");
        String formatTime = sdf.format(new Date(Long.parseLong(String.valueOf(time))));//时间戳转换成时间
        return formatTime;
    }
    /**
     * 得到总条数
     * @return
     */
    @Override
    public int getItemCount() {
        return staffEntityArrayList.size();
    }

    //自定义viewhodler
    class myViewHodler extends RecyclerView.ViewHolder {
        private TextView staff_staff_name;
        private TextView staff_group_name;
        private TextView staff_in_time;
        private TextView staff_near_time;
        private TextView staff_time_long;


        public myViewHodler(View itemView) {
            super(itemView);
            staff_staff_name = (TextView) itemView.findViewById(R.id.staff_staff_name);
            staff_group_name = (TextView) itemView.findViewById(R.id.staff_group_name);
            staff_in_time = (TextView) itemView.findViewById(R.id.staff_in_time);
            staff_near_time = (TextView) itemView.findViewById(R.id.staff_near_time);
            staff_time_long = (TextView) itemView.findViewById(R.id.staff_time_long);
            //点击事件放在adapter中使用，也可以写个接口在activity中调用
            //方法一：在adapter中设置点击事件
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //可以选择直接在本位置直接写业务处理
                    //Toast.makeText(context,"点击了xxx",Toast.LENGTH_SHORT).show();
                    //此处回传点击监听事件
                    if(onItemClickListener!=null){
                        onItemClickListener.OnItemClick(v, staffEntityArrayList.get(getLayoutPosition()));
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
        public void OnItemClick(View view, StaffEntity data);
    }

    //需要外部访问，所以需要设置set方法，方便调用
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}