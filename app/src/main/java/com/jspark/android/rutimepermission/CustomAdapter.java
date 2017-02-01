package com.jspark.android.rutimepermission;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by jsPark on 2017. 2. 1..
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomHolder> {

    ArrayList<Contacts> data;
    Context context;

    public CustomAdapter(ArrayList<Contacts> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public CustomHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        CustomHolder ch = new CustomHolder(view);
        return ch;
    }

    @Override
    public void onBindViewHolder(CustomHolder holder, int position) {
        final Contacts contact = this.data.get(position);

        holder.tvId.setText("" + contact.getId());
        holder.tvNm.setText(contact.getName());
        holder.tvPn.setText(contact.getPhoneNum());

        Animation anime = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
        holder.card.setAnimation(anime);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class CustomHolder extends RecyclerView.ViewHolder {

        TextView tvId, tvNm, tvPn;
        ImageButton btn;
        CardView card;

        public CustomHolder(View itemView) {
            super(itemView);
            tvId = (TextView)itemView.findViewById(R.id.txtId);
            tvNm = (TextView)itemView.findViewById(R.id.txtName);
            tvPn = (TextView)itemView.findViewById(R.id.txtNum);
            btn = (ImageButton)itemView.findViewById(R.id.btnCall);
            card = (CardView)itemView.findViewById(R.id.cardView);

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (context.checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                            Intent i = new Intent(Intent.ACTION_CALL, Uri.parse("tel: " + tvPn.getText()));
                            context.startActivity(i);
                        }
                    } else {
                        Intent i = new Intent(Intent.ACTION_CALL, Uri.parse("tel: " + tvPn.getText()));
                        context.startActivity(i);
                    }
                }
            });
        }
    }
}
