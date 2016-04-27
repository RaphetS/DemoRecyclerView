package com.raphets.recyclerview;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {
	private RecyclerView mRecyclerView;
	private List<String> mDatas;
	private MyRecylerViewAdapter adapter;
	private SwipeRefreshLayout mRefreshLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 初始化视图
		initView();
		// 初始化数据
		initData();

		// 下拉刷新
		mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {

				mDatas.clear();
				for (int i = 0; i <= 50; i++) {
					mDatas.add("item---" + new Random().nextInt(30));
				}
				adapter.notifyDataSetChanged();
				mRefreshLayout.setRefreshing(false);
			}
		});
		adapter = new MyRecylerViewAdapter(this, mDatas);
		// item点击事件
		adapter.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(View view, int postion) {
				Toast.makeText(MainActivity.this, postion + "", 1000).show();

			}
		});
		// 给RecyclerView绑定适配器
		mRecyclerView.setAdapter(adapter);
		// 添加分割线
		mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
		// 添加和移除item时候的动态效果
		mRecyclerView.setItemAnimator(new DefaultItemAnimator());
		// 布局管理器
		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
		mRecyclerView.setLayoutManager(linearLayoutManager);
		// 上拉自动加载
		mRecyclerView.setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
				super.onScrolled(recyclerView, dx, dy);
				LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
				int last = manager.findLastCompletelyVisibleItemPosition();
				int totalCount = manager.getItemCount();
				// last >= totalCount - 2表示剩余2个item是自动加载，可自己设置
				// dy>0表示向下滑动
				if (last >= totalCount - 2 && dy > 0) {

					/*
					 * 加载数据
					 */
					List<String> datas = new ArrayList<String>();
					for (int i = 0; i <= 10; i++) {
						datas.add("load" + new Random().nextInt(10));
					}
					//addData()是在自定义的Adapter中自己添加的方法，用来给list添加数据
					adapter.addData(datas);
				}
			}
		});

	}

	/*
	 * 初始化视图
	 */
	private void initView() {
		setContentView(R.layout.activity_main);
		mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
		mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
	}

	/*
	 * 初始化数据
	 */
	private void initData() {
		mDatas = new ArrayList<String>();
		for (int i = 0; i <= 50; i++) {
			mDatas.add("item---" + new Random().nextInt(30));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();
		switch (id) {
		case R.id.listview:
			mRecyclerView.setLayoutManager(new LinearLayoutManager(this, OrientationHelper.VERTICAL, true));
			break;
		case R.id.gridView:
			mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
			break;
		case R.id.horizonalListview:
			mRecyclerView.setLayoutManager(new LinearLayoutManager(this, OrientationHelper.HORIZONTAL, true));
			break;
		case R.id.horizonalGridview:
			mRecyclerView.setLayoutManager(new GridLayoutManager(this, 5, OrientationHelper.HORIZONTAL, false));
			break;
		case R.id.staggerdView:

			break;
		case R.id.add:
			adapter.notifyItemInserted(1);
			break;
		case R.id.delete:
			adapter.notifyItemRemoved(1);
			break;
		default:
			break;
		}

		return super.onOptionsItemSelected(item);
	}
}
