package com.example.aquabeing;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class NoteAdapterOrdersForDealer extends FirestoreRecyclerAdapter<NoteOrdersForDealer, NoteAdapterOrdersForDealer.NoteHolderOrdersForDealer> {


    public NoteAdapterOrdersForDealer(@NonNull FirestoreRecyclerOptions<NoteOrdersForDealer> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull NoteHolderOrdersForDealer holder, int position, @NonNull NoteOrdersForDealer model) {
        holder.cust_name.setText(model.getCustomer_name());
        holder.cust_address.setText(model.getCustomer_address());
        holder.brand.setText(model.getBrand());
        holder.type.setText(model.getType());
        holder.numberof.setText(model.getAmount());
        holder.total_price.setText(model.getTotal_price());
    }

    @NonNull
    @Override
    public NoteHolderOrdersForDealer onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item_orders_for_dealer,parent, false);
        return new NoteHolderOrdersForDealer(view);
    }

    class NoteHolderOrdersForDealer extends RecyclerView.ViewHolder{
        TextView cust_name, cust_address, brand, type, numberof, total_price;

        public NoteHolderOrdersForDealer(@NonNull View itemView) {
            super(itemView);
            cust_name = itemView.findViewById(R.id.tvcustomer_name_deal);
            cust_address = itemView.findViewById(R.id.tvcustomer_address_dealer);
            brand = itemView.findViewById(R.id.tvbrand_in_orders_deal);
            type = itemView.findViewById(R.id.tvtype_in_orders_deal);
            numberof = itemView.findViewById(R.id.tvnumber_in_orders_deal);
            total_price = itemView.findViewById(R.id.tvtotal_price_in_orders_deal);
        }
    }
}