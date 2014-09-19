package com.example.potatolib.base;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.ImageView;
import android.widget.TabWidget;

import com.example.potatolib.R;

public abstract class BaseTabPagerActivity extends BaseActivity implements
        OnPageChangeListener, OnTabChangeListener
{
	private ViewPager mPagerContent;
	protected int currentIndex;
	protected TabsAdapter mAdapter;
	protected TabHost mTabHost;
	protected TabWidget mTabWidget;
	private boolean isNeedReset;
	private int orientation;
	private final float SCALE_MAX = 0.7f;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.base_tab_pager);
		mPagerContent = (ViewPager) findViewById(R.id.pagercontent);
		mTabHost = (TabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup();
		mTabWidget = mTabHost.getTabWidget();
		mAdapter = new TabsAdapter(this, mTabHost);
		mPagerContent.setAdapter(mAdapter);
		mPagerContent.setOnPageChangeListener(this);
		mTabHost.setOnTabChangedListener(this);
		addTabs();
		if (savedInstanceState != null)
		{
			currentIndex = savedInstanceState.getInt("currentIndex");
		}
		mTabHost.setCurrentTab(currentIndex);
	}

	protected void onSaveInstanceState(Bundle outState)
	{
		outState.putInt("currentIndex", currentIndex);
		super.onSaveInstanceState(outState);
	}

	/**
	 * 设置Tab切换
	 * 
	 * @param tabIndex
	 *            切换的Tab下标
	 */
	protected void setTabChange(int tabIndex)
	{
		if (tabIndex != currentIndex)
		{
			mTabHost.setCurrentTab(tabIndex);
		}
	}

	/**
	 * 获取当前Tab下标
	 * 
	 * @return Tab下标
	 */
	protected int getTabPosition()
	{
		return currentIndex;
	}

	/**
	 * 子类需要实现add tab的方法
	 * 如：mTabsAdapter.addTab(mTabHost.newTabSpec("custom").setIndicator
	 * ("Custom"), LoaderCourseListSupport.CourseListFragment.class, null);
	 */
	protected abstract void addTabs();

	/**
	 * 添加Tab子页面
	 * 
	 * @param tabText
	 *            Tab导航名称
	 * @param type
	 *            Tab类型
	 * @param cls
	 *            Tab页Class对象
	 * @param bundle
	 *            传递参数
	 */
	protected void addTab(View tabView, Class<?> cls, Bundle bundle)
	{
		mAdapter.addTab(tabView, cls, bundle);
	}

	public void onPageScrolled(int arg0, float arg1, int arg2)
	{
		Log.e("arg0", arg0 + "");
		Log.e("arg1", arg1 + "");
		if (arg1 == 0)
		{
			isNeedReset = true;
		}
		else
		{
			if (isNeedReset)
			{
				if (arg1 > 0.5)
				{
					orientation = 1; // 向左滑
				}
				else
				{
					orientation = 2; // 向右滑
				}
				isNeedReset = false;
			}
			else
			{
				if (orientation == 1)
				{
					ImageView tv1 = (ImageView) mAdapter.getTabInfo(arg0 + 1).view;
					ImageView tv2 = (ImageView) mAdapter.getTabInfo(arg0).view;
					tv1.setAlpha(1f - SCALE_MAX * (1 - arg1));
					tv2.setAlpha(0.3f + SCALE_MAX * (1 - arg1));
				}
				else if (orientation == 2)
				{
					ImageView tv1 = (ImageView) mAdapter.getTabInfo(arg0).view;
					ImageView tv2 = (ImageView) mAdapter.getTabInfo(arg0 + 1).view;
					tv1.setAlpha(1f - SCALE_MAX * arg1);
					tv2.setAlpha(0.3f + SCALE_MAX * arg1);
				}
			}
		}
	}

	public void onPageScrollStateChanged(int arg0)
	{

	}

	public void onPageSelected(int position)
	{
		if (currentIndex != position)
		{
			currentIndex = position;
			mTabHost.setCurrentTab(position);
			mAdapter.getTabInfo(position).view.setAlpha(1);
		}
	}

	public void onTabChanged(String tabId)
	{
		if (currentIndex != mTabHost.getCurrentTab())
		{
			currentIndex = mTabHost.getCurrentTab();
			mPagerContent.setCurrentItem(currentIndex);
		}
	}

	public static class TabsAdapter extends FragmentStatePagerAdapter
	{
		private Context context;
		private final List<TabInfo> mTabs = new ArrayList<TabInfo>();
		private TabHost mTabHost;

		public TabsAdapter(BaseActivity activity, TabHost mTabHost)
		{
			super(activity.getSupportFragmentManager());
			context = activity;
			this.mTabHost = mTabHost;
		}

		public void addTab(View tabView, Class<?> cls, Bundle bundle)
		{
			TabSpec tabSpec = mTabHost.newTabSpec(cls.getSimpleName());
			tabSpec.setIndicator(tabView);
			tabSpec.setContent(new DummyTabFactory(context));
			mTabHost.addTab(tabSpec);
			mTabs.add(new TabInfo(cls, bundle, tabView));
			notifyDataSetChanged();
		}

		public void removeTab(TabInfo tab)
		{
			if (mTabs.contains(tab))
			{
				mTabs.remove(tab);
				notifyDataSetChanged();
			}
		}

		public void removeAllTab()
		{
			mTabs.removeAll(mTabs);
			notifyDataSetChanged();
		}

		public TabInfo getTabInfo(int position)
		{
			return mTabs.get(position);
		}

		public Fragment getItem(int position)
		{
			TabInfo info = mTabs.get(position);
			return Fragment
			        .instantiate(context, info.clss.getName(), info.args);
		}

		public void destroyItem(ViewGroup container, int position, Object object)
		{
		}

		public int getCount()
		{
			return mTabs.size();
		}

		public static class DummyTabFactory implements
		        TabHost.TabContentFactory
		{
			private final Context mContext;

			public DummyTabFactory(Context context)
			{
				mContext = context;
			}

			public View createTabContent(String tag)
			{
				View v = new View(mContext);
				v.setMinimumWidth(0);
				v.setMinimumHeight(0);
				return v;
			}
		}

		public static class TabInfo
		{
			public final Class<?> clss;
			public final Bundle args;
			public final View view;

			public TabInfo(Class<?> _clss, Bundle _args, View _view)
			{
				clss = _clss;
				args = _args;
				view = _view;
			}
		}
	}
}
