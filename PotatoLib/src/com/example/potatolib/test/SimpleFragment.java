package com.example.potatolib.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.potatolib.base.BaseFragment;

public class SimpleFragment extends BaseFragment
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
	        @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
	{
		TextView tv = new TextView(getActivity());
		tv.setText("simple");
		return tv;
	}
}
