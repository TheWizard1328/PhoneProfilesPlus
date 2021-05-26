package sk.henrichg.phoneprofilesplus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class RingtonePreferenceAdapterX extends BaseAdapter {

    final Map<String, String> toneList;
    private final RingtonePreferenceFragmentX preferenceFragment;

    //private final LayoutInflater inflater;
    private final Context context;

    RingtonePreferenceAdapterX(RingtonePreferenceFragmentX preferenceFragment, Context c,
                               Map<String, String> toneList)
    {
        this.preferenceFragment = preferenceFragment;
        this.toneList = toneList;

        //inflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = c;
    }

    public int getCount() {
        return toneList.size();
    }

    public Object getItem(int position) {
        List<String> uris = new ArrayList<>(toneList.keySet());
        String uri;
        uri = uris.get(position);
        return uri;
    }

    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder {
        TextView ringtoneLabel;
        TextView ringtonePath;
        RadioButton radioBtn;
        //int position;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder;

        String ringtone = (new ArrayList<>(toneList.keySet())).get(position);
        String ringtoneTitle = (new ArrayList<>(toneList.values())).get(position);

        View vi = convertView;
        if (convertView == null) {
            vi = LayoutInflater.from(context).inflate(R.layout.ringtone_preference_list_item, parent, false);

            holder = new ViewHolder();
            holder.ringtoneLabel = vi.findViewById(R.id.ringtone_pref_dlg_item_label);
            holder.ringtonePath = vi.findViewById(R.id.ringtone_pref_dlg_item_path);
            holder.radioBtn = vi.findViewById(R.id.ringtone_pref_dlg_item_radiobtn);
            vi.setTag(holder);
        }
        else
        {
            holder = (ViewHolder)vi.getTag();
        }

        holder.radioBtn.setTag(ringtone);
        holder.radioBtn.setChecked((preferenceFragment.preference.ringtoneUri != null) && preferenceFragment.preference.ringtoneUri.equals(ringtone));
        holder.radioBtn.setOnClickListener(v -> {
            RadioButton rb = (RadioButton) v;
            preferenceFragment.preference.setRingtone((String)rb.getTag(), false);
            preferenceFragment.preference.playRingtone();
        });

        holder.ringtoneLabel.setText(ringtoneTitle);
        if (ringtone.contains("content://media/internal")) {
            holder.ringtonePath.setVisibility(View.VISIBLE);
            holder.ringtonePath.setText(R.string.ringtone_pref_dlg_system_tone);
        }
        else
        if (ringtone.contains("content://media/external")) {
            holder.ringtonePath.setVisibility(View.VISIBLE);
            holder.ringtonePath.setText(R.string.ringtone_pref_dlg_extenal_tone);
        }
        else {
            /*if (ringtoneUri != null) {
                try {
                    FileUtils fileUtils = new FileUtils(context);
                    holder.ringtonePath.setText(fileUtils.getPath(ringtoneUri));
                } catch (Exception e) {
                    holder.ringtonePath.setVisibility(View.GONE);
                }
            } else*/
                holder.ringtonePath.setVisibility(View.GONE);
        }

        return vi;
    }

}
