package com.example.orderapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    private static RVClickListener mylistener;
    private ArrayList<Order> menulist;
    public OrderAdapter(ArrayList<Order> menulist, RVClickListener rvcl) {
        this.menulist = menulist;
        mylistener=rvcl;
    }

    @NonNull
    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater=LayoutInflater.from(viewGroup.getContext());
        View v=inflater.inflate(R.layout.row_item_order,viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.textView_qty_type.setText(menulist.get(i).getType());
        viewHolder.textView_toppings.setText("with Toppings: "+menulist.get(i).getToppings().toString());
        viewHolder.textView_subtotal.setText("Rp "+menulist.get(i).getSubtotal());
    }

    @Override
    public int getItemCount() {
        return (menulist!=null)?menulist.size():0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView_qty_type,textView_toppings,textView_subtotal;
        public ViewHolder(View v) {
            super(v);
            textView_qty_type=itemView.findViewById(R.id.textView_qty_type);
            textView_toppings=itemView.findViewById(R.id.textView_toppings);
            textView_subtotal=itemView.findViewById(R.id.textView_subtotal);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mylistener.recyclerViewListClicked(v, ViewHolder.this.getLayoutPosition());
                }
            });
        }
    }
}
