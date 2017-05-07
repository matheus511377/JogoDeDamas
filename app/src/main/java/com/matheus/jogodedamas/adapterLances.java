package com.matheus.jogodedamas;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.matheus.jogodedamas.Classes.Lance;

import java.util.List;

/**
 * Created by Matheus on 07/05/2017.
 */

public class adapterLances extends BaseAdapter {
    private final List<Lance> listLances;
    private final Activity act;

    public adapterLances(List<Lance> listLance, Activity act) {
        this.listLances = listLance;
        this.act = act;
    }

    @Override
    public int getCount() {
        return listLances.size();
    }

    @Override
    public Object getItem(int position) {
        return listLances.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = act.getLayoutInflater()
                .inflate(R.layout.lance, parent, false);
        Lance lance = listLances.get(position);
        //pegando as referências das Views
        TextView txtLance = (TextView)
                view.findViewById(R.id.txtLance);
        txtLance.setText(lance.getLance());
        return view;
    }
}
