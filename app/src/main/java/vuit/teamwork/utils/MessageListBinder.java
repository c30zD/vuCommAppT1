/*
 * Created on 16/11/2014.
 *
 * Modified version of BinderData.java by Srivatsa Haridas
 * http://www.codeproject.com/Members/vatsag
 *
 * Project website:
 * http://www.codeproject.com/Articles/507651/Customized-Android-ListView-with-Image-and-Text
 */

package vuit.teamwork.utils;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import vuit.teamwork.R;

/**
 * Binds data to be displayed on {@link vuit.teamwork.ui.MessageList}
 *
 * @author c30zD
 */
public class MessageListBinder extends BaseAdapter {
    // Keys to data collection go here
    /* XML node keys
    static final String KEY_TAG = "weatherdata"; // parent node
    static final String KEY_ID = "id";
    static final String KEY_CITY = "city";
    static final String KEY_TEMP_C = "tempc";
    static final String KEY_TEMP_F = "tempf";
    static final String KEY_CONDN = "condition";
    static final String KEY_SPEED = "windspeed";
    static final String KEY_ICON = "photo";
    */

    private LayoutInflater inflater;

    private ViewHolder holder;

    // Data collection declaration goes here
    // List<HashMap<String,String>> weatherDataCollection;

    public MessageListBinder(Activity activity) {       // TODO 2nd argument: data to inflate from
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return 0;       // dataCollection.size
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (convertView == null) {
            vi = inflater.inflate(R.layout.message_list_row_layout, null);
            holder = new ViewHolder();
            holder.photo = (ImageView) vi.findViewById(R.id.messageContactPhoto);
            holder.lblContactNames = (TextView) vi.findViewById(R.id.messageParticipants);
            holder.lblPreview = (TextView) vi.findViewById(R.id.messagePreview);
            vi.setTag(holder);
        } else {
            holder = (ViewHolder) vi.getTag();
        }
        // Setting all values in ListView
        // TODO get real values from DB
        holder.lblContactNames.setText("John Doe, Pepe, Homer Simpson");

        //holder.photo
        /*
        //Setting an image
	    String uri = "drawable/"+ weatherDataCollection.get(position).get(KEY_ICON);
	    int imageResource = vi.getContext().getApplicationContext().getResources().getIdentifier(uri, null, vi.getContext().getApplicationContext().getPackageName());
	    Drawable image = vi.getContext().getResources().getDrawable(imageResource);
	    holder.tvWeatherImage.setImageDrawable(image);

        */

        holder.lblPreview.setText("Blah, blah, blah.");

        // TODO Get contact ID to pass on to an intent?

        return vi;
    }

    static class ViewHolder {
        TextView lblContactNames;
        ImageView photo;
        TextView lblPreview;
    }
}
