package com.raphets.recyclerview;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

/**
 * 自定义的ViewHolder继承自android.support.v7.widget.RecyclerView.ViewHolder
 * 
 * @author raphets
 *
 */
public class MyViewHolder extends ViewHolder implements OnClickListener {
	TextView textView;
	// View rootView;
	private OnItemClickListener mListener;// 声明自定义的接口

	// 构造函数中添加自定义的接口的参数
	public MyViewHolder(View itemView, OnItemClickListener listener) {
		super(itemView);
		// this.rootView = itemView;//将itemView传出去
		mListener = listener;
		// 为ItemView添加点击事件
		itemView.setOnClickListener(this);

		textView = (TextView) itemView.findViewById(R.id.textView);

	}

	@Override
	public void onClick(View v) {
		// getpostion()为Viewholder自带的一个方法，用来获取RecyclerView当前的位置，将此作为参数，传出去
		mListener.onItemClick(v, getPosition());
	}

}
