package com.krishna.iparker.User;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.krishna.iparker.ModelClass;
import com.krishna.iparker.R;

public class CustomInfoWindowGoogleMap implements GoogleMap.InfoWindowAdapter {

    private Context context;

    public CustomInfoWindowGoogleMap(Context ctx) {
        context = ctx;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View view = ((Activity) context).getLayoutInflater()
                .inflate(R.layout.map_custom_infowindow, null);

        TextView pname = view.findViewById(R.id.txtPname);
        TextView avail_slots = view.findViewById(R.id.txtAvailSlots);
        TextView rate = view.findViewById(R.id.txtRate);
        TextView ptype = view.findViewById(R.id.txtptype);

      /*  ImageView img = view.findViewById(R.id.pic);

        TextView hotel_tv = view.findViewById(R.id.hotels);
        TextView food_tv = view.findViewById(R.id.food);
        TextView transport_tv = view.findViewById(R.id.transport);*/

        pname.setText(marker.getTitle());
        avail_slots.setText(marker.getSnippet());

        ModelClass modelClass = (ModelClass) marker.getTag();

        /*int imageId = context.getResources().getIdentifier(infoWindowData.getImage().toLowerCase(),
                "drawable", context.getPackageName());
        img.setImageResource(imageId);*/

        pname.setText(modelClass.getPname());
        avail_slots.setText("Available slots "+modelClass.getAvailslots());
        rate.setText("Rate "+modelClass.getRate());
        ptype.setText("Type: "+modelClass.getPtype());


        return view;
    }
}
