package com.example.itemsdatabasestartup;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OrderItemListRecyclerView extends RecyclerView.Adapter<OrderItemListRecyclerView.ViewHolder> {

     public static ArrayList<ModelClassForOrdersList> orderList;
     Context context;
     SQLiteHelper sqLiteHelper;
     SQLiteDatabase sqLiteDatabase;

     public OrderItemListRecyclerView(ArrayList<ModelClassForOrdersList> orderList)
    {
        this.orderList = orderList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         context = parent.getContext();
        sqLiteHelper = new SQLiteHelper(context);
        sqLiteDatabase = sqLiteHelper.getReadableDatabase();

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orderslist_layout,parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
         holder.itemName.setText(orderList.get(position).getName());
         holder.itemquantity.setText(String.valueOf(orderList.get(position).getQuantity()) );
         holder.Total.setText(String.valueOf(orderList.get(position).getTotal()));
         holder.Description.setText(String.valueOf(orderList.get(position).getDescription()));
    }



    @Override
    public int getItemCount()
    {
        return orderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        private TextView itemName;
        private EditText itemquantity , Description;
        private ImageButton DeleteItemFromOrderList;
        private TextView Total;
        int ID;
        String Name;
        float price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemName = itemView.findViewById(R.id.ItemNameinOrderList);
            itemquantity = itemView.findViewById(R.id.ItemquantityEditText);
            DeleteItemFromOrderList = itemView.findViewById(R.id.DeleteItemFromOrdersList);
            Total = itemView.findViewById(R.id.totalTextView);
            Description = itemView.findViewById(R.id.orderItemDescription);

            itemquantity.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    try {
                        if (TextUtils.isEmpty(editable.toString()))
                        {
                           Total.setText("");
                            orderList.get(getAdapterPosition()).setQuantity(0);
                            orderList.get(getAdapterPosition()).setTotal(0);

                        }
                        else {
                            ID = orderList.get(getAdapterPosition()).getId();
                            Name = orderList.get(getAdapterPosition()).getName();
                            int quantity = Integer.parseInt(editable.toString());

                            String query = " select price from itemtally where id ='" + ID + "';";
                            Cursor cursor = sqLiteDatabase.rawQuery(query, null);
                            if (cursor != null) {
                                while (cursor.moveToNext()) {
                                    price = cursor.getFloat(0);
                                    Total.setText(Float.toString(quantity * price));
                                    orderList.get(getAdapterPosition()).setQuantity(quantity);
                                    orderList.get(getAdapterPosition()).setTotal(quantity * price);
                                }
                            }
                        }
                    }
                    catch (NumberFormatException e)
                    {
                        e.printStackTrace();
                    }

                }
            });


            Description.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {

                    orderList.get(getAdapterPosition()).setDescription(editable.toString());

                }
            });

            DeleteItemFromOrderList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    orderList.remove(getAdapterPosition());
                    notifyDataSetChanged();
                }
            });

        }
    }
}
