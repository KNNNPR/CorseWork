package com.example.corsework;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<Item> cartItemList;
    private OnItemRemovedListener onItemRemovedListener;

    public CartAdapter(List<Item> cartItemList, OnItemRemovedListener onItemRemovedListener) {
        this.cartItemList = cartItemList;
        this.onItemRemovedListener = onItemRemovedListener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Item currentItem = cartItemList.get(position);
        holder.imageView.setImageResource(currentItem.getImageResource());
        holder.nameTextView.setText(currentItem.getName());
        holder.priceTextView.setText(currentItem.getPrice());

        holder.removeFromCartButton.setOnClickListener(v -> {
            cartItemList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, cartItemList.size());
            onItemRemovedListener.onItemRemoved();
        });
    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
    }

    public interface OnItemRemovedListener {
        void onItemRemoved();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView nameTextView;
        public TextView priceTextView;
        public Button removeFromCartButton;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_image);
            nameTextView = itemView.findViewById(R.id.item_name);
            priceTextView = itemView.findViewById(R.id.item_price);
            removeFromCartButton = itemView.findViewById(R.id.remove_from_cart_button);
        }
    }
}
