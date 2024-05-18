package com.example.corsework;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;


import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;


import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity implements CartAdapter.OnItemRemovedListener {

    private RecyclerView cartRecyclerView;
    private CartAdapter cartAdapter;
    private List<Item> cartItemList;
    private TextView totalPriceTextView;
    private Button backToMainButton;
    private double totalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cartRecyclerView = findViewById(R.id.cartRecyclerView);
        totalPriceTextView = findViewById(R.id.totalPriceTextView);
        backToMainButton = findViewById(R.id.backToMainButton);
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        cartItemList = new ArrayList<>();
        cartAdapter = new CartAdapter(cartItemList, this);
        cartRecyclerView.setAdapter(cartAdapter);

        // Получение переданных данных из MainActivity
        if (getIntent() != null && getIntent().getExtras() != null) {
            List<Item> items = (List<Item>) getIntent().getSerializableExtra("cartItems");
            if (items != null) {
                cartItemList.addAll(items);
                calculateTotalPrice();
                cartAdapter.notifyDataSetChanged();
            }
        }

        backToMainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("updatedCartItems", (ArrayList<Item>) cartItemList);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }

    private void calculateTotalPrice() {
        totalPrice = 0;
        for (Item item : cartItemList) {
            totalPrice += Double.parseDouble(item.getPrice().replace("$", ""));
        }
        totalPriceTextView.setText("Total Price: $" + totalPrice);
    }

    @Override
    public void onItemRemoved() {
        calculateTotalPrice();
    }
}