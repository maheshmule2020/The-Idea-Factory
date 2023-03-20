package com.krishna.iparker.User;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.krishna.iparker.ModelClass;
import com.krishna.iparker.R;

import java.util.ArrayList;

class AcceptRequestAdapter extends RecyclerView.Adapter<AcceptRequestAdapter.ConfirmViewHolder> {

    private Context mCtx;
    private ArrayList<ModelClass> orderList;

    public AcceptRequestAdapter(Context mCtx, ArrayList<ModelClass> orderList) {
        this.mCtx = mCtx;
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public ConfirmViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.adapter_req_accept, null);

        return new ConfirmViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ConfirmViewHolder holder, int position) {
        ModelClass modelclass = orderList.get(position);
        holder.mTxtConfirm.setText(modelclass.getConfirmed());
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class ConfirmViewHolder extends RecyclerView.ViewHolder {
        private TextView mTxtConfirm;

        public ConfirmViewHolder(@NonNull View itemView) {
            super(itemView);
            mTxtConfirm = itemView.findViewById(R.id.txtconfirmatio);

        }
    }

}