package aksahysahai.lmdelivery;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.CardViewHolder> {

    Context context;

    //this arrayList has all the recieved respone in form of an array of class DeliveryGetSet
    ArrayList<DeliveryGetSet> deliveryGetSets;


    public RecyclerViewAdapter(Context context, ArrayList<DeliveryGetSet> deliveryGetSets) {
        this.context = context;
        this.deliveryGetSets = deliveryGetSets;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.main_card_layout, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, final int position) {


        // .setDefaultRequestOptions is used to cache the images, so that they can be reused when the device is offline
        holder.card_tv.setText(deliveryGetSets.get(position).getDescription());
        Glide.with(context)
                .setDefaultRequestOptions(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                .load(deliveryGetSets.get(position).getImageUrl())
                .into(holder.card_iv);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,MapsActivity.class);
                intent.putExtra("imageUrl",deliveryGetSets.get(position).getImageUrl());
                intent.putExtra("description",deliveryGetSets.get(position).getDescription());
                intent.putExtra("lat",deliveryGetSets.get(position).getLocationGetSet().getLat());
                intent.putExtra("lng",deliveryGetSets.get(position).getLocationGetSet().getLng());
                intent.putExtra("address",deliveryGetSets.get(position).getLocationGetSet().getAddress());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return deliveryGetSets.size();
    }


    public class CardViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        AppCompatImageView card_iv;
        TextView card_tv;

        public CardViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            card_iv = itemView.findViewById(R.id.card_iv);
            card_tv = itemView.findViewById(R.id.card_tv);
        }
    }
}
