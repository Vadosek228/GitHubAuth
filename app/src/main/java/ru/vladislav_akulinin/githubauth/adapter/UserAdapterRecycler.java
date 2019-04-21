package ru.vladislav_akulinin.githubauth.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.List;

import ru.vladislav_akulinin.githubauth.R;
import ru.vladislav_akulinin.githubauth.model.User;

// Унаследовали наш адаптер от RecyclerView.Adapter
// Здесь же указали наш собственный ViewHolder, который предоставит нам доступ к View-компонентам
public class UserAdapterRecycler extends RecyclerView.Adapter<UserAdapterRecycler.UserViewHolder> {

    private List<User> userList;
    private Context mContext;

    public boolean picasso = true;

    public UserAdapterRecycler(Context mContext, List<User> objects, boolean picasso){
        this.userList = objects;
        this.mContext = mContext;
        this.picasso = picasso;
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
        private String html_url; //для получения ФИО, копания и email

        private UserViewHolder(View itemView){
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            textViewLogin = (TextView) itemView.findViewById(R.id.textViewLogin);
            textViewId = (TextView) itemView.findViewById(R.id.textViewId);
        }

        private void bind(final User user){
            textViewId.setText(user.getId());
            textViewLogin.setText(user.getLogin());

            if(picasso) {
                picasso(user);
            }else {
                glide(mContext, user);
            }

            html_url = user.getHtml_url();
        }

        private void picasso(User user){
            Picasso.get().load(user.getAvatarUrl()).placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher).into(imageView);
        }

        private void glide(Context context, User user){
            Glide
                    .with(context)
                    .load(user.getAvatarUrl())
                    .centerCrop()
                    .placeholder(R.mipmap.ic_launcher)
                    .into(imageView);
        }

    }

    //очистить если нужно
    public void clearItems() {
        userList.clear();
        notifyDataSetChanged();
    }
}
