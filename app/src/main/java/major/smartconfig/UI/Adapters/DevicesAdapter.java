package major.smartconfig.UI.Adapters;

/**
 * Created by Rahul on 7/3/2015.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import major.smartconfig.Entity.Device;
import major.smartconfig.R;
import major.smartconfig.UI.AllDevicesActivity;
import major.smartconfig.UI.DeviceViewActivity;

public class DevicesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "DevicesAdapt";
    private Context context;
    private List<Device> items;
    private int itemLayout;


    public DevicesAdapter(Context context, List<Device> items, int itemLayout){
        this.items = items;
        this.itemLayout = itemLayout;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        return new CardViewHolder(context,v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        CardViewHolder holder = (CardViewHolder) viewHolder;

        final Device device = items.get(position);
        holder.name.setText(device.getName());
        holder.ip.setText(device.getIpAddress());
        holder.currentConfig.setText(device.getCurrentConfig());

        if(device.getPhotoUri() != null)
            holder.photo.setImageURI(device.getPhotoUri());
    }


    @Override
    public int getItemCount() {
        return items.size();
    }


    public void add(Device item, int position) {
        items.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(Device item) {
        int position = items.indexOf(item);
        items.remove(position);
        notifyItemRemoved(position);
    }


    public static class CardViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{
        TextView name;
        ImageView photo;
        TextView currentConfig;
        TextView ip;
        Context context;

        public CardViewHolder(Context context,View itemView) {
            super(itemView);
            this.context = context;
            itemView.setOnClickListener(this);

            name = (TextView) itemView.findViewById(R.id.name);
            photo = (ImageView) itemView.findViewById(R.id.photo);
            currentConfig = (TextView) itemView.findViewById(R.id.current_config);
            ip = (TextView) itemView.findViewById(R.id.ip_address);

        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(AllDevicesActivity.home, DeviceViewActivity.class);
            intent.putExtra("title",name.getText().toString());
            intent.putExtra("current",currentConfig.getText().toString());
            (context).startActivity(intent);
        }
    }

}

