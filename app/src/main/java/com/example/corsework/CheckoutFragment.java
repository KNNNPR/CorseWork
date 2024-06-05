package com.example.corsework;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class CheckoutFragment extends DialogFragment {

    private EditText nameEditText;
    private EditText addressEditText;
    private EditText cityEditText;
    private EditText postalCodeEditText;
    private Button confirmButton;
    private List<Item> cartItemList;
    private CartActivity cartActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_checkout, container, false);

        nameEditText = view.findViewById(R.id.nameEditText);
        addressEditText = view.findViewById(R.id.addressEditText);
        cityEditText = view.findViewById(R.id.cityEditText);
        postalCodeEditText = view.findViewById(R.id.postalCodeEditText);
        confirmButton = view.findViewById(R.id.confirmButton);

        cartActivity = (CartActivity) getActivity();
        if (cartActivity != null) {
            cartItemList = cartActivity.getCartItemList();
        }

        confirmButton.setOnClickListener(v -> {
            if (validateInput()) {
                completePurchase();
            }
        });

        return view;
    }

    private boolean validateInput() {
        if (nameEditText.getText().toString().trim().isEmpty() ||
                addressEditText.getText().toString().trim().isEmpty() ||
                cityEditText.getText().toString().trim().isEmpty() ||
                postalCodeEditText.getText().toString().trim().isEmpty()) {
            Toast.makeText(getActivity(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void completePurchase() {
        Toast.makeText(getActivity(), "Purchase Completed", Toast.LENGTH_SHORT).show();
        if (cartItemList != null) {
            cartItemList.clear();
            cartActivity.updateCart();
        }
        dismiss();
    }
}
