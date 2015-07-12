package uit.linh.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import models.MessageModel;
import uit.linh.suppersms.R;
import models.MessageTypes;

/**
 *
 * Created by linh on 16/05/2015.
 */
public class MessageAdapter extends ArrayAdapter<MessageModel> {
    private Context context;
    private int resource;
    private ArrayList<MessageModel> arr;


    public MessageAdapter(Context context, int resource, ArrayList<MessageModel> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.arr = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = ((Activity)context).getLayoutInflater().inflate(resource, null);
        TextView txtMessageItem = (TextView) convertView.findViewById(R.id.txt_message_item);
        String sPosition = position +"";
        if(position<10){
            sPosition = "0"+position;
        }
        txtMessageItem.setText( sPosition +". "+arr.get(position).toString());


        switch (arr.get(position).getCategoryId()){
            case 1:
                txtMessageItem.setBackgroundResource(R.drawable.sms_item_border_radius_pink_dim);
                break;

            case 2:
                txtMessageItem.setBackgroundResource(R.drawable.sms_item_border_radius_orange_dim);
                break;

            case 3:
                txtMessageItem.setBackgroundResource(R.drawable.sms_item_border_radius_blue_dim);
                break;

            case 4:
                txtMessageItem.setBackgroundResource(R.drawable.sms_item_border_radius_blue_dim);
                break;
            case 5:
                txtMessageItem.setBackgroundResource(R.drawable.sms_item_border_radius_purple_dim);
                break;

        }

        return convertView;
    }
}
