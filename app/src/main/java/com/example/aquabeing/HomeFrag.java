package com.example.aquabeing;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HomeFrag extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fraghome,container,false);
        ImageButton bisleri = view.findViewById(R.id.b1);
        ImageButton tata = view.findViewById(R.id.b2);
        ImageButton bailey = view.findViewById(R.id.b3);
        ImageButton aquafina = view.findViewById(R.id.b4);
        ImageButton kinley = view.findViewById(R.id.b5);
        ImageButton twenty_lit = view.findViewById(R.id.b11);
        ImageButton five_lit = view.findViewById(R.id.b12);
        ImageButton one_lit = view.findViewById(R.id.b13);
        ImageButton fivehundred_ml = view.findViewById(R.id.b14);
        ImageButton twohundred_ml = view.findViewById(R.id.b15);


        bisleri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Dealer_list.class);
                startActivity(intent);


            }
        });

        return view;

    }
}
