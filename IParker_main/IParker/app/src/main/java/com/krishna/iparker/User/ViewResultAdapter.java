package com.krishna.iparker.User;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.krishna.iparker.ModelClass;
import com.krishna.iparker.R;


import java.util.ArrayList;

public class ViewResultAdapter extends RecyclerView.Adapter<ViewResultAdapter.OrdersViewHolder> {

    private Context mCtx;
    private ArrayList<ModelClass> candidateList;

    public ViewResultAdapter(Context mCtx, ArrayList<ModelClass> candidateList) {
        this.mCtx = mCtx;
        this.candidateList = candidateList;
    }


    @Override
    public OrdersViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.adapter_view_result, null);

        return new OrdersViewHolder(view);
    }

    @Override
    public void onBindViewHolder( OrdersViewHolder holder, int position) {

        ModelClass modelClass = candidateList.get(position);
        holder.mTxtName.setText("Your account balance:"+modelClass.getAcc_balance());
    }

    @Override
    public int getItemCount() {
        return candidateList.size();
    }

    public class OrdersViewHolder extends RecyclerView.ViewHolder {

        private TextView mTxtName;

        public OrdersViewHolder( View itemView) {
            super(itemView);

            mTxtName= (TextView) itemView.findViewById(R.id.txtrPartyName);
        }
    }


}
