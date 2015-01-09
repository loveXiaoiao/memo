package com.xiao.memo.adapter;

import java.util.ArrayList;
import java.util.List;

import com.xiao.memo.R;
import com.xiao.memo.entiy.Record;
import com.xiao.memo.util.TimeUtil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListViewAdapter extends BaseAdapter {
	
	private final Context context;
	private List<Record> records = new ArrayList<Record>();

	public ListViewAdapter(Context context, List<Record> records) {
		this.context = context;
		if (records != null) this.records = records;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return records.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			// 使用Vliew的对象itemView与R.layout.item关联
			convertView = inflater.inflate(R.layout.list_item, null);
			holder = new ViewHolder();
			holder.content = (TextView) convertView.findViewById(R.id.itemTitle);
			holder.expireTime = (TextView) convertView.findViewById(R.id.itemText);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Record record = records.get(position);
		holder.content.setText(record.getContent());
		holder.expireTime.setText(TimeUtil.parseToString(record.getExpireTime()));
		return convertView;
	}
	private class ViewHolder {
		public TextView content;
		public TextView expireTime;
	}

}
