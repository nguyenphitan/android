package com.example.crud_th1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.crud_th1.model.Model;
import com.example.crud_th1.model.ModelAdapter;
import com.example.crud_th1.model.SpinnerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ModelAdapter.ModelItemListener, SearchView.OnQueryTextListener {

    private Spinner sp;
    private RecyclerView recyclerView;
    private ModelAdapter adapter;
    private EditText eName, eDescribe, ePrice;
    private Button btAdd, btUpdate;
    private SearchView searchView;

    private int positionCurrent;
    private int[] imgs = {R.drawable.cat_1, R.drawable.cat2, R.drawable.cat3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        adapter = new ModelAdapter(this);
        LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.setClickListener(this);

        // Search
        searchView.setOnQueryTextListener(this);

        // OnClick btn ADD
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Model model = new Model();
                int i = sp.getSelectedItemPosition();
                String name = eName.getText().toString();
                String desc = eDescribe.getText().toString();
                String p = ePrice.getText().toString();

                int img = 0;
                double price = 0;
                try {
                    img = imgs[i];
                    price = Double.parseDouble(p);

                    // set to model
                    model.setImg(img);
                    model.setName(name);
                    model.setDescribe(desc);
                    model.setPrice(price);

                    // add to adapter
                    adapter.add(model);
                } catch (NumberFormatException e) {
                    Toast.makeText(getApplicationContext(), "Nhap sai dinh dang!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        // click update
        btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Model model = new Model();
                int i = sp.getSelectedItemPosition();
                String name = eName.getText().toString();
                String desc = eDescribe.getText().toString();
                String p = ePrice.getText().toString();

                int img = 0;
                double price = 0;
                try {
                    img = imgs[i];
                    price = Double.parseDouble(p);

                    // set to model
                    model.setImg(img);
                    model.setName(name);
                    model.setDescribe(desc);
                    model.setPrice(price);

                    // update to adapter
                    adapter.update(positionCurrent, model);
                    btAdd.setEnabled(true);
                    btUpdate.setEnabled(false);
                } catch (NumberFormatException e) {
                    Toast.makeText(getApplicationContext(), "Nhap sai dinh dang!", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void initView() {
        sp = findViewById(R.id.img);
        SpinnerAdapter adapter = new SpinnerAdapter(this);
        sp.setAdapter(adapter);
        recyclerView = findViewById(R.id.recycleView);
        eName = findViewById(R.id.name);
        eDescribe = findViewById(R.id.describe);
        ePrice = findViewById(R.id.price);
        btAdd = findViewById(R.id.btAdd);
        btUpdate = findViewById(R.id.btUpdate);
        btUpdate.setEnabled(false);
        searchView = findViewById(R.id.search);

    }

    @Override
    public void onItemClick(View view, int position) {
        btAdd.setEnabled(false);
        btUpdate.setEnabled(true);
        positionCurrent = position;
        Model model = adapter.getItem(position);

        int img = model.getImg();
        int p = 0;
        for(int i = 0 ; i < imgs.length ; i++) {
            if(img == imgs[i]) {
                p = i;
                break;
            }
        }

        sp.setSelection(p);
        eName.setText(model.getName());
        eDescribe.setText(model.getDescribe());
        ePrice.setText(model.getPrice() + "");

    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        filter(s);
        return false;
    }

    private void filter(String s) {
        List<Model> filterList = new ArrayList<>();
        for(Model i : adapter.getListBackup()) {
            if(i.getName().toLowerCase().contains(s.toLowerCase())) {
                filterList.add(i);
            }
        }

        if(filterList.isEmpty()) {
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
        } else {
            adapter.filterList(filterList);
        }

    }
}