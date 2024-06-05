package com.example.corsework;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private List<Item> itemList;
    private DatabaseReference databaseReference;
    private List<Item> cartItemList;

    public ItemAdapter(List<Item> itemList, DatabaseReference databaseReference, List<Item> cartItemList) {
        this.itemList = itemList;
        this.databaseReference = databaseReference;
        this.cartItemList = cartItemList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item currentItem = itemList.get(position);
        Glide.with(holder.itemView.getContext())
                .load(currentItem.getImageUrl())
                .into(holder.imageView);
        holder.nameTextView.setText(currentItem.getName());
        holder.priceTextView.setText(currentItem.getPrice());

        holder.addToCartButton.setOnClickListener(v -> {
            // Добавить товар в список товаров в корзине
            cartItemList.add(currentItem);
//            // Создать новый экземпляр Item с уникальным ключом для базы данных
//            String key = databaseReference.push().getKey();
//            Item cartItem = new Item(currentItem.getImageUrl(), currentItem.getName(), currentItem.getPrice(), key);
//            // Добавить новый экземпляр Item в базу данных
//            databaseReference.child(key).setValue(cartItem);
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView nameTextView;
        public TextView priceTextView;
        public Button addToCartButton;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_image);
            nameTextView = itemView.findViewById(R.id.item_name);
            priceTextView = itemView.findViewById(R.id.item_price);
            addToCartButton = itemView.findViewById(R.id.add_to_cart_button);
        }
    }
}
