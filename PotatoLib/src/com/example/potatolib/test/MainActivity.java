package com.example.potatolib.test;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar.Tab;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.potatolib.R;
import com.example.potatolib.base.BaseTabPagerActivity;

public class MainActivity extends BaseTabPagerActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		/*
		 * mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		 * mActionBar .addTab(mActionBar .newTab() .setText("simple")
		 * .setTabListener( new TabListener<>(this, "simple",
		 * SimpleFragment.class))); mActionBar.addTab(mActionBar .newTab()
		 * .setText("contact") .setTabListener( new TabListener<>(this,
		 * "contact", ContactsFragment.class)));
		 */
	}

	@Override
	protected void addTabs()
	{
		ImageView tv = new ImageView(getApplicationContext());
		tv.setImageResource(R.drawable.ic_launcher);
		tv.setAlpha(1f);
		addTab(tv, SimpleFragment.class, null);
		ImageView tv1 = new ImageView(getApplicationContext());
		tv1.setImageResource(R.drawable.ic_launcher);
		tv1.setAlpha(0.3f);
		addTab(tv1, ContactsFragment.class, null);
		ImageView tv2 = new ImageView(getApplicationContext());
		tv2.setImageResource(R.drawable.ic_launcher);
		tv2.setAlpha(0.3f);
		addTab(tv2, AppsFragment.class, null);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.action, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case R.id.simple:
				break;
		}
		return false;
	}

	public static class TabListener<T extends Fragment> implements
	        ActionBar.TabListener
	{
		private final ActionBarActivity mActivity;
		private final String mTag;
		private final Class<T> mClass;
		private final Bundle mArgs;
		private Fragment mFragment;

		public TabListener(ActionBarActivity activity, String tag, Class<T> clz)
		{
			this(activity, tag, clz, null);
		}

		public TabListener(ActionBarActivity activity, String tag,
		        Class<T> clz, Bundle args)
		{
			mActivity = activity;
			mTag = tag;
			mClass = clz;
			mArgs = args;
			// Check to see if we already have a fragment for this tab, probably
			// from a previously saved state. If so, deactivate it, because our
			// initial state is that a tab isn't shown.
			mFragment = mActivity.getSupportFragmentManager()
			        .findFragmentByTag(mTag);
			if (mFragment != null && !mFragment.isDetached())
			{
				FragmentTransaction ft = mActivity.getSupportFragmentManager()
				        .beginTransaction();
				ft.detach(mFragment);
				ft.commit();
			}
		}

		public void onTabSelected(Tab tab, FragmentTransaction ft)
		{
			if (mFragment == null)
			{
				mFragment = Fragment.instantiate(mActivity, mClass.getName(),
				        mArgs);
				ft.add(android.R.id.content, mFragment, mTag);
			}
			else
			{
				ft.attach(mFragment);
			}
		}

		public void onTabUnselected(Tab tab, FragmentTransaction ft)
		{
			if (mFragment != null)
			{
				ft.detach(mFragment);
			}
		}

		public void onTabReselected(Tab tab, FragmentTransaction ft)
		{
			Toast.makeText(mActivity, "Reselected!", Toast.LENGTH_SHORT).show();
		}
	}

}
