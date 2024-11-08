package com.example.bookingapp;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import java.util.List;
import java.util.Map;

public class CustomExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> listGroupTitles;
    private Map<String, List<String>> listChildItems;

    public CustomExpandableListAdapter(Context context, List<String> listGroupTitles,
                                       Map<String, List<String>> listChildItems) {
        this.context = context;
        this.listGroupTitles = listGroupTitles;
        this.listChildItems = listChildItems;
    }

    @Override
    public int getGroupCount() {
        return listGroupTitles.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return listChildItems.get(listGroupTitles.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listGroupTitles.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listChildItems.get(listGroupTitles.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String title = (String) getGroup(groupPosition);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(android.R.layout.simple_expandable_list_item_1, parent, false);
        }
        TextView textView = convertView.findViewById(android.R.id.text1);
        textView.setText(title);
        textView.setBackgroundColor(context.getResources().getColor(android.R.color.white)); // Fond blanc pour l'élément du groupe
        textView.setTextColor(context.getResources().getColor(android.R.color.black)); // Couleur du texte noir
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String title = (String) getChild(groupPosition, childPosition);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false);
        }
        TextView textView = convertView.findViewById(android.R.id.text1);
        textView.setText(title);
        textView.setBackgroundColor(context.getResources().getColor(android.R.color.white)); // Fond blanc pour l'élément enfant
        textView.setTextColor(context.getResources().getColor(android.R.color.black)); // Couleur du texte noir
        return convertView;
    }



    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}


