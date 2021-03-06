package com.xia.walkingnavigator;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.R.id.list;

public class OfficeListActivity extends ListActivity {
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_office_list);
        showItems();
 //       listView=(ListView)findViewById(R.id.list);
    }

    private void showItems(){
        List<Office> offices = DBManager.getDBManager(this).queryOffice();
        Log.d("OfficeListActivity",offices.size()+"");
        ArrayList<Map<String, Integer>> list = new ArrayList<Map<String, Integer>>();
        Log.d("starting showing office","");
        for (Office office : offices) {
            HashMap<String, Integer> map = new HashMap<String, Integer>();
            Log.d(office.name,office.id+"");
            map.put(office.name, office.id);
            list.add(map);
        }
        SimpleAdapter adapter = new SimpleAdapter(this, list, android.R.layout.simple_list_item_2,
                new String[]{"name", "id"}, new int[]{android.R.id.text1, android.R.id.text2});
        setListAdapter(adapter);
    }
}
