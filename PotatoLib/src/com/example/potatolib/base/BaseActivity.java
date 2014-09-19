package com.example.potatolib.base;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;

/**
 * Activity»ùÀà
 * 
 * @author wjy
 * 
 */
public class BaseActivity extends ActionBarActivity
{
	protected ActionBar mActionBar;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		mActionBar = getSupportActionBar();
	}

	@Override
	protected void onResume()
	{
		super.onResume();
	}

	@Override
	protected void onPause()
	{
		super.onPause();
	}

	public void setTitle(String title)
	{
		if (title != null)
			mActionBar.setTitle(title);
	}
}
