package com.example.corsework;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.corsework.databinding.ActivityLoginBinding;
import com.example.corsework.databinding.ActivityMainBinding;

import java.util.ArrayList;


import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import java.util.ArrayList;
import java.util.List;


import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ItemAdapter itemAdapter;
    private List<Item> itemList;
    private List<Item> cartItemList;
    private Button goToCartButton;

    private static final int CART_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rvBoards);
        goToCartButton = findViewById(R.id.goToCartButton);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        itemList = new ArrayList<>();
        cartItemList = new ArrayList<>();

        // Заполнение списка данными
        itemList.add(new Item(R.drawable.ic_launcher_foreground, "Item 1", "$10"));
        itemList.add(new Item(R.drawable.ic_launcher_foreground, "Item 2", "$20"));
        itemList.add(new Item(R.drawable.ic_launcher_foreground, "Item 3", "$30"));

        itemAdapter = new ItemAdapter(itemList, cartItemList);
        recyclerView.setAdapter(itemAdapter);

        goToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CartActivity.class);
                intent.putExtra("cartItems", (ArrayList<Item>) cartItemList);
                startActivityForResult(intent, CART_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CART_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null && data.getExtras() != null) {
                List<Item> updatedCartItems = (List<Item>) data.getSerializableExtra("updatedCartItems");
                if (updatedCartItems != null) {
                    cartItemList.clear();
                    cartItemList.addAll(updatedCartItems);
                    itemAdapter.notifyDataSetChanged();
                }
            }
        }
    }
}