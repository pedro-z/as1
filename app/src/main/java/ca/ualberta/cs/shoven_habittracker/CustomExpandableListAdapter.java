package ca.ualberta.cs.shoven_habittracker;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by shoven on 2016-10-02.
 */

public class CustomExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> listTitles;
    private HashMap<String, List<Date>> listDetails;

    public CustomExpandableListAdapter(Context context, List<String> listTitles, HashMap<String, List<Date>> listDetails) {
        this.context = context;
        this.listTitles = listTitles;
        this.listDetails = listDetails;
    }

    @Override
    public int getGroupCount() {
        return listTitles.size();
    }

    @Override
    public int getChildrenCount(int groupId) {
        return listDetails.get(listTitles.get(groupId)).size();
    }

    @Override
    public Object getGroup(int groupId) {
        return listTitles.get(groupId);
    }

    @Override
    public Object getChild(int groupId, int childId) {
        return listDetails.get(listTitles.get(groupId)).get(childId);
    }

    @Override
    public long getGroupId(int groupId) {
        return groupId;
    }

    @Override
    public long getChildId(int groupId, int childId) {
        return childId;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String listGroupText = getGroup(groupPosition).toString();

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_group, null);
        }
        TextView listGroupTextView = (TextView) convertView.findViewById(R.id.listGroupTitle);
        listGroupTextView.setTypeface(null, Typeface.BOLD);
        listGroupTextView.setText(listGroupText);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String listItemText = getChild(groupPosition, childPosition).toString();

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_group_item, null);
        }
        TextView listItemTextView = (TextView) convertView.findViewById(R.id.listItem);
        listItemTextView.setText(listItemText);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupId, int childId) {
        return true;
    }
}
