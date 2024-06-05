//package com.example.corsework;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.TextView;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class CartActivity extends AppCompatActivity implements CartAdapter.OnItemRemovedListener {
//
//    public static List<Item> cartItemList = new ArrayList<>();
//
//    private RecyclerView cartRecyclerView;
//    private CartAdapter cartAdapter;
//    private TextView totalPriceTextView;
//    private Button backToMainButton;
//    private double totalPrice;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_cart);
//
//        cartRecyclerView = findViewById(R.id.cartRecyclerView);
//        totalPriceTextView = findViewById(R.id.totalPriceTextView);
//        backToMainButton = findViewById(R.id.backToMainButton);
//        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        cartAdapter = new CartAdapter(cartItemList, this);
//        cartRecyclerView.setAdapter(cartAdapter);
//
//        // Получение переданных данных из MainActivity
//        if (getIntent() != null && getIntent().getExtras() != null) {
//            List<Item> items = (List<Item>) getIntent().getSerializableExtra("cartItems");
//            if (items != null) {
//                cartItemList.clear();
//                cartItemList.addAll(items);
//                calculateTotalPrice();
//                cartAdapter.notifyDataSetChanged();
//            }
//        }
//
//        backToMainButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent resultIntent = new Intent();
//                resultIntent.putExtra("updatedCartItems", (ArrayList<Item>) cartItemList);
//                setResult(RESULT_OK, resultIntent);
//                finish();
//            }
//        });
//    }
//
//    private void calculateTotalPrice() {
//        totalPrice = 0;
//        for (Item item : cartItemList) {
//            totalPrice += Double.parseDouble(item.getPrice().replace("$", ""));
//        }
//        totalPriceTextView.setText("Total Price: $" + totalPrice);
//    }
//
//    @Override
//    public void onItemRemoved() {
//        calculateTotalPrice();
//    }
//}
package com.example.corsework;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity implements CartAdapter.OnItemRemovedListener {

    public static List<Item> cartItemList = new ArrayList<>();

    private RecyclerView cartRecyclerView;
    private CartAdapter cartAdapter;
    private TextView totalPriceTextView;
    private Button backToMainButton;
    private Button checkoutButton;
    private double totalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cartRecyclerView = findViewById(R.id.cartRecyclerView);
        totalPriceTextView = findViewById(R.id.totalPriceTextView);
        backToMainButton = findViewById(R.id.backToMainButton);
        checkoutButton = findViewById(R.id.checkoutButton);
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        cartAdapter = new CartAdapter(cartItemList, this);
        cartRecyclerView.setAdapter(cartAdapter);

        if (getIntent() != null && getIntent().getExtras() != null) {
            List<Item> items = (List<Item>) getIntent().getSerializableExtra("cartItems");
            if (items != null) {
                cartItemList.clear();
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

        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCheckoutFragment();
            }
        });

        updateCheckoutButtonState();
    }

    private void calculateTotalPrice() {
        totalPrice = 0;
        for (Item item : cartItemList) {
            totalPrice += Double.parseDouble(item.getPrice().replace("$", ""));
        }
        totalPriceTextView.setText("Total Price: $" + totalPrice);
        updateCheckoutButtonState();
    }

    @Override
    public void onItemRemoved() {
        calculateTotalPrice();
    }

    private void showCheckoutFragment() {
        DialogFragment checkoutFragment = new CheckoutFragment();
        checkoutFragment.show(getSupportFragmentManager(), "CheckoutFragment");
    }

    public List<Item> getCartItemList() {
        return cartItemList;
    }

    public void updateCart() {
        cartAdapter.notifyDataSetChanged();
        calculateTotalPrice();
    }

    private void updateCheckoutButtonState() {
        if (cartItemList.isEmpty()) {
            checkoutButton.setEnabled(false);
        } else {
            checkoutButton.setEnabled(true);
        }
    }
}
