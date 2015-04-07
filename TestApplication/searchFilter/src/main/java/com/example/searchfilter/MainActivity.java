package com.example.searchfilter;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;

public class MainActivity extends Activity implements OnQueryTextListener, TextWatcher {
	private static final String TAG = "MainActivity";

	private SearchView searchView;
	private EditText searchEditText;
	private ListView listView;

	private MyAdapter adapter;
	private ArrayAdapter<String> arrAdapter;
	private String[] dataArr = { "aaa", "aab", "bbb", "ccc", "ddd", "abc", "abd",
			"ddb", "cda", "cbd", "dda", "ccb", "cba", "adc" };
	private ArrayList<String> data = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		searchView = (SearchView) findViewById(R.id.search_view);
		searchView.setOnQueryTextListener(this);
		searchEditText = (EditText) findViewById(R.id.search_edit_text);
		searchEditText.addTextChangedListener(this);
		listView = (ListView) findViewById(R.id.list_view);
		listView.setTextFilterEnabled(true);
		data.addAll(Arrays.asList(dataArr));
		adapter = new MyAdapter();
		//arrAdapter = new ArrayAdapter<String>(this, R.layout.item_main_list, R.id.text, data);
		listView.setAdapter(adapter);
		
	}

	private class MyAdapter extends BaseAdapter implements Filterable {

		private MyFilter filter;
		private LayoutInflater inflater = getLayoutInflater();
		private ArrayList<String> dataList = data;

		@Override
		public int getCount() {
			return dataList.size();
		}

		@Override
		public Object getItem(int position) {
			return dataList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = inflater.inflate(R.layout.item_main_list, null);
				holder.textView = (TextView) convertView
						.findViewById(R.id.text);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.textView.setText(dataList.get(position));
			return convertView;
		}

		private class ViewHolder {
			TextView textView;
		}

		@Override
		public Filter getFilter() {
			if(filter == null) {
				filter = new MyFilter();
			}
			return filter;
		}
		
		private class MyFilter extends Filter {

			@Override
			protected FilterResults performFiltering(CharSequence constraint) {
				Log.i(TAG, "performFiltering constraint=" + constraint);
				FilterResults results = new FilterResults();
				ArrayList<String> list = new ArrayList<String>();
				if(TextUtils.isEmpty(constraint)) {
					list.addAll(data);
				} else {
					String constraintString = constraint.toString().toLowerCase();
					for(String s : data) {
						if(s.toLowerCase().contains(constraintString)) {
							list.add(s);
						}
					}
				}
				results.values = list;
				results.count = list.size();
				return results;
			}

			@Override
			protected void publishResults(CharSequence constraint,
					FilterResults results) {
				Log.i(TAG, "publishResults constraint=" + constraint);
				dataList = (ArrayList<String>) results.values;
				notifyDataSetChanged();
			}
			
		}

	}

	//=====================SearchView
	@Override
	public boolean onQueryTextSubmit(String query) {
		Log.i(TAG, "onQueryTextSubmit query=" + query);
		return false;
	}

	@Override
	public boolean onQueryTextChange(String newText) {
		Log.i(TAG, "onQueryTextChange newText=" + newText);
		adapter.getFilter().filter(newText);
		return false;
	}

	//=====================EditText
	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		Log.i(TAG, "onTextChanged s=" + s);
		adapter.getFilter().filter(s);
	}

	@Override
	public void afterTextChanged(Editable s) {
		
	}

}
