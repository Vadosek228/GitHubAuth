package ru.vladislav_akulinin.githubauth.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ru.vladislav_akulinin.githubauth.R;
import ru.vladislav_akulinin.githubauth.model.User;

public class UserAdapter extends ArrayAdapter<User> {

    private List<User> userList;
    private Context context;
    private LayoutInflater inflater;

    public UserAdapter(Context context, List<User> objects){
        super(context, 0, objects);
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        userList = objects;
    }

    @Nullable
    @Override
    public User getItem(int position) {
        return userList.get(position);
    }

    @NonNull
    @Override
    public View getView(int possition, View convertView, @NonNull ViewGroup parent){
        final ViewHolder vh;
        if(convertView == null){
            View view = inflater.inflate(R.layout.list_item, parent, false);
            vh = ViewHolder.create((RelativeLayout) view);
            view.setTag(vh);
        }else {
            vh = (ViewHolder) convertView.getTag();
        }

        User item = getItem(possition);

        assert item != null;
        vh.textViewId.setText(item.getId());
        vh.textViewLogin.setText(item.getLogin());
        Picasso.get().load(item.getAvatarUrl()).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(vh.imageView);

        return vh.rootView;
    }

    private static class ViewHolder {
        final RelativeLayout rootView;
        public final ImageView imageView;
        public final TextView textViewLogin;
        public final TextView textViewId;

        private ViewHolder(RelativeLayout rootView, ImageView imageView, TextView textViewLogin, TextView textViewId){
            this.rootView = rootView;
            this.imageView = imageView;
            this.textViewLogin = textViewLogin;
            this.textViewId = textViewId;
        }

        static ViewHolder create(RelativeLayout rootView){
            ImageView imageView = (ImageView) rootView.findViewById(R.id.imageView);
            TextView textViewLogin = (TextView) rootView.findViewById(R.id.textViewLogin);
            TextView textViewId = (TextView) rootView.findViewById(R.id.textViewId);
            return new ViewHolder(rootView, imageView, textViewLogin, textViewId);
        }
    }
}
