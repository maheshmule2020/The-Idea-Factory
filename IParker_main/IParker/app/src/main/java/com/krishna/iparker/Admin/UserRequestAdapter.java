package com.krishna.iparker.Admin;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.krishna.iparker.ModelClass;
import com.krishna.iparker.R;

import java.util.ArrayList;

public class UserRequestAdapter extends RecyclerView.Adapter<UserRequestAdapter.OrdersViewHolder> {

    private Context mCtx;
    private ArrayList<ModelClass> orderList;

    public UserRequestAdapter(Context mCtx, ArrayList<ModelClass> orderList) {
        this.mCtx = mCtx;
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrdersViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.adapter_user_request
                , null);

        return new OrdersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersViewHolder holder, int position) {

        ModelClass modelClass = orderList.get(position);
        holder.mTxtUserdetails.setText(modelClass.getDetails());
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class OrdersViewHolder extends RecyclerView.ViewHolder {

        private TextView mTxtUserdetails;
        private Button mBtnAccept;
        public OrdersViewHolder(@NonNull View itemView) {
            super(itemView);

            mTxtUserdetails=itemView.findViewById(R.id.txtUserDetails);
            mBtnAccept=itemView.findViewById(R.id.btnAccept);

            mBtnAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ModelClass modelClass=orderList.get(getAdapterPosition());
                    mClickListener.onAcceptClick(modelClass);
                    mBtnAccept.setEnabled(false);
                }
            });

        }
    }

    public interface AcceptClickListener {
        void onAcceptClick(ModelClass modelClass);
    }
    private AcceptClickListener mClickListener;

    public void setAcceptClickListener(AcceptClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }



}
