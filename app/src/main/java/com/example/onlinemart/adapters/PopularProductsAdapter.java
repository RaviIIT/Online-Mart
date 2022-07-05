package com.example.onlinemart.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.onlinemart.R;
import com.example.onlinemart.activities.DetailedActivity;
import com.example.onlinemart.models.NewProductsModel;
import com.example.onlinemart.models.PopularProductsModel;

import java.util.List;

public class PopularProductsAdapter extends RecyclerView.Adapter<PopularProductsAdapter.ViewHolder> {

    private Context context;
    private List<PopularProductsModel> list;

    public PopularProductsAdapter(Context context, List<PopularProductsModel> list)
    {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_products, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(list.get(position).getImg_url()).into(holder.allImg);
        holder.allName.setText(list.get(position).getName());
        holder.allPrice.setText(String.valueOf(list.get(position).getPrice()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailedActivity.class);
                intent.putExtra("detailed", list.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView allImg;
        TextView allName, allPrice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            allImg = itemView.findViewById(R.id.all_img);
            allName = itemView.findViewById(R.id.all_product_name);
            allPrice = itemView.findViewById(R.id.all_price);
        }
    }
}
