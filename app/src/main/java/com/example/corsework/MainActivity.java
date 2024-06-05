package com.example.corsework;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    private List<Item> deckItemList;
    private List<Item> gripItemList;
    private List<Item> tracksItemList;
    private List<Item> wheelsItemList;
    private List<Item> cartItemList;
    private Button goToCartButton;
    private static final int CART_REQUEST_CODE = 1;
    private static final String TAG = "MainActivity";
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Инициализировать Firebase
        FirebaseApp.initializeApp(this);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Инициализировать RecyclerView и Button
        recyclerViewCategory1 = findViewById(R.id.recyclerViewCategory1);
        recyclerViewCategory2 = findViewById(R.id.recyclerViewCategory2);
        recyclerViewCategory3 = findViewById(R.id.recyclerViewCategory3);
        recyclerViewCategory4 = findViewById(R.id.recyclerViewCategory4);
        goToCartButton = findViewById(R.id.goToCartButton);

        // Установить LayoutManager для RecyclerView
        recyclerViewCategory1.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewCategory2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewCategory3.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewCategory4.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        // Инициализировать списки товаров
        deckItemList = new ArrayList<>();
        gripItemList = new ArrayList<>();
        tracksItemList = new ArrayList<>();
        wheelsItemList = new ArrayList<>();
        cartItemList = new ArrayList<>();

        // Создать экземпляры ItemAdapter для RecyclerView
        itemAdapterCategory1 = new ItemAdapter(deckItemList, mDatabase, cartItemList);
        itemAdapterCategory2 = new ItemAdapter(gripItemList, mDatabase, cartItemList);
        itemAdapterCategory3 = new ItemAdapter(tracksItemList, mDatabase, cartItemList);
        itemAdapterCategory4 = new ItemAdapter(wheelsItemList, mDatabase, cartItemList);

        // Установить экземпляры ItemAdapter для RecyclerView
        recyclerViewCategory1.setAdapter(itemAdapterCategory1);
        recyclerViewCategory2.setAdapter(itemAdapterCategory2);
        recyclerViewCategory3.setAdapter(itemAdapterCategory3);
        recyclerViewCategory4.setAdapter(itemAdapterCategory4);

        // Установить слушатель нажатий на кнопку goToCartButton
        goToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CartActivity.class);
                intent.putExtra("cartItems", (ArrayList<Item>) cartItemList);
                startActivityForResult(intent, CART_REQUEST_CODE);
            }
        });

        // Загрузить товары из базы данных Firebase
        loadItemsFromFirebase();
    }

    private void loadItemsFromFirebase() {
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d(TAG, "onDataChange: " + snapshot.getValue());
                // Очистить списки товаров
                deckItemList.clear();
                gripItemList.clear();
                tracksItemList.clear();
                wheelsItemList.clear();

                // Загрузить товары из категории "Deck"
                for (DataSnapshot itemSnapshot : snapshot.child("Deck").getChildren()) {
                    Item item = itemSnapshot.getValue(Item.class);
                    deckItemList.add(item);
                }
                itemAdapterCategory1.notifyDataSetChanged();

                // Загрузить товары из категории "Grip"
                for (DataSnapshot itemSnapshot : snapshot.child("Grip").getChildren()) {
                    Item item = itemSnapshot.getValue(Item.class);
                    gripItemList.add(item);
                }
                itemAdapterCategory2.notifyDataSetChanged();

                // Загрузить товары из категории "Tracks"
                for (DataSnapshot itemSnapshot : snapshot.child("Tracks").getChildren()) {
                    Item item = itemSnapshot.getValue(Item.class);
                    tracksItemList.add(item);
                }
                itemAdapterCategory3.notifyDataSetChanged();

                // Загрузить товары из категории "Wheels"
                for (DataSnapshot itemSnapshot : snapshot.child("Wheels").getChildren()) {
                    Item item = itemSnapshot.getValue(Item.class);
                    wheelsItemList.add(item);
                }
                itemAdapterCategory4.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
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
                }
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_about) {
            displayAboutDialog();
            return true;
        } else if (id == R.id.action_author) {
            displayAuthorDialog();
            return true;
        } else if (id == R.id.action_exit) {
            displayExitConfirmationDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void displayAboutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("О программе")
                .setMessage("Тригонометрический кальуклятро - необходимый каждому инженеру инструмент. С помощью него легко можно найти нужыне значения для совершенно извращенных значений углов, которых нет в тригонометрической таблице")
                .setPositiveButton("OK", null)
                .show();
    }

    private void displayAuthorDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Об авторе")
                .setMessage("Козлов Кирилл Игоревич\nИКБО-13-22")
                .setPositiveButton("ОК", null)
                .show();
    }

    private void displayExitConfirmationDialog() {
        ExitConfirmationDialogFragment dialogFragment = new ExitConfirmationDialogFragment();
        dialogFragment.show(getSupportFragmentManager(), "exit_dialog");
    }
}
