package com.example.aquabeing;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

public class NoteAdapter extends FirestoreRecyclerAdapter<Note, NoteAdapter.NoteHolder> {

    NoteHolder noteHolder;
    String documentId=" ";
    Context context;

    public NoteAdapter(@NonNull FirestoreRecyclerOptions<Note> options) {
        super(options);
        
    }





    @Override
    protected void onBindViewHolder(@NonNull final NoteHolder holder, final int position, @NonNull Note model) {
        holder.tvdealer_name.setText(model.getName());
        holder.tvdealer_address.setText(model.getAddress());
       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               DocumentSnapshot snapshot = getSnapshots().getSnapshot(holder.getAdapterPosition());
               documentId=snapshot.getId();
//               Intent I=new Intent(NoteAdapter.this, dealer_products_list.class);
////
////               Bundle bundle = new Bundle();
////                         bundle.putString("dealers",documentId);
//////               Intent activityBstartIntent = new Intent(NoteAdapter.this, dealer_products_list.class);
//////               activityBstartIntent.putExtra("key",documentId);
////               I.putExtras(bundle);
               onItemClickListner.onClick(documentId);


           }
       });



    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item_dealer,parent, false);

        return new NoteHolder(view);
    }

    class NoteHolder extends RecyclerView.ViewHolder {
        TextView tvdealer_name, tvdealer_address;
        public NoteHolder(@NonNull View itemView) {
            super(itemView);
            tvdealer_name = itemView.findViewById(R.id.tvdealer_name_cust);
            tvdealer_address = itemView.findViewById(R.id.tvdealer_address_cust);

            //listener set on ENTIRE ROW, you may set on individual components within a row.
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    mClickListener.onItemClick(v, getAdapterPosition());
//                    Log.d("dealerID TAG",documentId);
//                    //                             String Dealerid = fs.collection("dealers").document().getId();
//  //           Bundle bundle = new Bundle();
//    //           bundle.putString("dealers",documentId);
////               Intent activityBstartIntent = new Intent(NoteAdapter.this, dealer_products_list.class);
////               activityBstartIntent.putExtra("key",documentId);
//
////                    LocalBroadcastManager.getInstance(context).sendBroadcast(activityBstartIntent);
//
////                    Intent intent = new Intent("custom-message");
////                    //            intent.putExtra("quantity",Integer.parseInt(quantity.getText().toString()));
////                    intent.putExtra("key",documentId);
////                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
//
//
//                }
//            });
        }

    }

    onItemClickListner onItemClickListner;

    public void setOnItemClickListner(onItemClickListner onItemClickListner) {
        this.onItemClickListner = onItemClickListner;
    }

    public interface onItemClickListner{
        void onClick(String str);//pass your object types.
    }

}
