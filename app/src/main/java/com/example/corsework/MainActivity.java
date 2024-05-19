package com.example.corsework;

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

    private RecyclerView recyclerViewCategory1;
    private RecyclerView recyclerViewCategory2;
    private RecyclerView recyclerViewCategory3;
    private RecyclerView recyclerViewCategory4;
    private ItemAdapter itemAdapterCategory1;
    private ItemAdapter itemAdapterCategory2;
    private ItemAdapter itemAdapterCategory3;
    private ItemAdapter itemAdapterCategory4;
    private List<Item> itemListCategory1;
    private List<Item> itemListCategory2;
    private List<Item> itemListCategory3;
    private List<Item> itemListCategory4;
    private List<Item> cartItemList;
    private Button goToCartButton;

    private static final int CART_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewCategory1 = findViewById(R.id.recyclerViewCategory1);
        recyclerViewCategory2 = findViewById(R.id.recyclerViewCategory2);
        recyclerViewCategory3 = findViewById(R.id.recyclerViewCategory3);
        recyclerViewCategory4 = findViewById(R.id.recyclerViewCategory4);
        goToCartButton = findViewById(R.id.goToCartButton);

        recyclerViewCategory1.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewCategory2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewCategory3.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewCategory4.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        itemListCategory1 = new ArrayList<>();
        itemListCategory2 = new ArrayList<>();
        itemListCategory3 = new ArrayList<>();
        itemListCategory4 = new ArrayList<>();
        cartItemList = new ArrayList<>();

        // Заполнение списков данными
        // Замените эти данные на свои
        itemListCategory1.add(new Item(R.drawable.ic_launcher_foreground, "Item 1", "$10"));
        itemListCategory1.add(new Item(R.drawable.ic_launcher_foreground, "Item 2", "$20"));

        itemListCategory2.add(new Item(R.drawable.ic_launcher_foreground, "Item A", "$15"));
        itemListCategory2.add(new Item(R.drawable.ic_launcher_foreground, "Item B", "$25"));

        itemListCategory3.add(new Item(R.drawable.ic_launcher_foreground, "Item X", "$12"));
        itemListCategory3.add(new Item(R.drawable.ic_launcher_foreground, "Item Y", "$18"));

        itemListCategory4.add(new Item(R.drawable.ic_launcher_foreground, "Item Alpha", "$30"));
        itemListCategory4.add(new Item(R.drawable.ic_launcher_foreground, "Item Beta", "$40"));

        itemAdapterCategory1 = new ItemAdapter(itemListCategory1, cartItemList);
        itemAdapterCategory2 = new ItemAdapter(itemListCategory2, cartItemList);
        itemAdapterCategory3 = new ItemAdapter(itemListCategory3, cartItemList);
        itemAdapterCategory4 = new ItemAdapter(itemListCategory4, cartItemList);

        recyclerViewCategory1.setAdapter(itemAdapterCategory1);
        recyclerViewCategory2.setAdapter(itemAdapterCategory2);
        recyclerViewCategory3.setAdapter(itemAdapterCategory3);
        recyclerViewCategory4.setAdapter(itemAdapterCategory4);

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
                    itemAdapterCategory1.notifyDataSetChanged();
                    itemAdapterCategory2.notifyDataSetChanged();
                    itemAdapterCategory3.notifyDataSetChanged();
                    itemAdapterCategory4.notifyDataSetChanged();
                }
            }
        }
    }
}