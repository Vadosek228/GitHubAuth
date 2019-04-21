package ru.vladislav_akulinin.githubauth.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ru.vladislav_akulinin.githubauth.Interface.OnItemClickListener;
import ru.vladislav_akulinin.githubauth.R;
import ru.vladislav_akulinin.githubauth.model.User;

// Унаследовали наш адаптер от RecyclerView.Adapter
// Здесь же указали наш собственный ViewHolder, который предоставит нам доступ к View-компонентам
public class UserAdapterRecycler extends RecyclerView.Adapter<UserAdapterRecycler.UserViewHolder> {

    private List<User> userList;
    private Context mContext;

    public UserAdapterRecycler(Context mContext, List<User> objects){
        this.userList = objects;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {

        final View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item, viewGroup, false);

        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder userViewHolder, int position) { //связь объекта и view
        userViewHolder.bind(userList.get(position));
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    //предоставляем прямую ссылку на каждый View-компонент
    //используется для кеширования View-компонентов и последующего быстрого доступа к ними
    class UserViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView textViewLogin;
        private TextView textViewId;

        private UserViewHolder(View itemView){
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            textViewLogin = (TextView) itemView.findViewById(R.id.textViewLogin);
            textViewId = (TextView) itemView.findViewById(R.id.textViewId);
        }

        private void bind(final User user){
            textViewId.setText(user.getId());
            textViewLogin.setText(user.getLogin());
            Picasso.get().load(user.getAvatarUrl()).placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher).into(imageView);
        }
    }

    //очистить если нужно
    public void clearItems() {
        userList.clear();
        notifyDataSetChanged();
    }
}
