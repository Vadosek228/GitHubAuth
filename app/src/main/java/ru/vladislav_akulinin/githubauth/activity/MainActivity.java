package ru.vladislav_akulinin.githubauth.activity;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.widget.Toolbar;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.vladislav_akulinin.githubauth.adapter.UserAdapterRecycler;
import ru.vladislav_akulinin.githubauth.api.ApiService;
import ru.vladislav_akulinin.githubauth.utils.InternetConnection;
import ru.vladislav_akulinin.githubauth.R;
import ru.vladislav_akulinin.githubauth.api.RetrofitClient;
import ru.vladislav_akulinin.githubauth.model.User;
import ru.vladislav_akulinin.githubauth.adapter.RecyclerClickListner;

public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();
//    private UserAdapter adapter;
//    private ListView listView;
    private List<User> userArrayList;
    FloatingActionButton fab;
    private String login;

    private View parentView;

    private RecyclerView recyclerView;
    private UserAdapterRecycler adapter;

    private boolean picasso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        parentView = findViewById(R.id.parentLayout);
        fab = (FloatingActionButton) findViewById(R.id.fab);

//        listView = (ListView) findViewById(R.id.list);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Snackbar.make(parentView, userArrayList.get(position).getFollowersUrl() + " | "
//                                            + userArrayList.get(position).getFollowingUrl(), Snackbar.LENGTH_LONG).show();
//            }
//        });

        initRecyclerView();
    }

    @Override
    protected void onResume() {
        super.onResume();

        onClick();
    }

    private void onClick(){
        assert  fab != null;
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(@NonNull final View v) {
                //проверка интернет соединения
                if(InternetConnection.checkConnection(getApplicationContext())){
                    final ProgressDialog dialog;

                    //ProgressDialog для взаимодействия с пользователем
                    dialog = new ProgressDialog(MainActivity.this);
                    dialog.setTitle(getString(R.string.string_gettings_json_title));
                    dialog.setMessage(getString(R.string.string_gettings_json_title));
                    dialog.show();

                    //создание объекта api interface
                    ApiService api = RetrofitClient.getApiService();

                    //calling json (запрос)
                    Call<List<User>> call = api.getMyJSON();

                    //обратный вызов будет, после получения ответа
                    ((retrofit2.Call) call).enqueue(new Callback<List<User>>() { //делаем запрос
                        @Override
                        public void onResponse(@NonNull retrofit2.Call<List<User>> call, @NonNull Response<List<User>> response) {
                            //если успешно

                            dialog.dismiss(); //скрываем диалог

                            if(response.isSuccessful()){ //если успешно получили
//                                userArrayList = response.body().getUser();
                                userArrayList = response.body();

                                //создаем адаптер
                                adapter = new UserAdapterRecycler(MainActivity.this, userArrayList,picasso);
                                recyclerView.setAdapter(adapter);

                                recyclerClick();

                            }else {
                                Snackbar.make(parentView, R.string.string_some_thing_wrong, Snackbar.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(@NonNull retrofit2.Call<List<User>> call, @NonNull Throwable t) {
                            //если не успешно
                            dialog.dismiss();
                        }
                    });
                }else {
                    Snackbar.make(parentView, R.string.string_internet_connection_not_available, Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); //устанавливаем линейный список отображения
    }

    private void recyclerClick(){
        recyclerView.addOnItemTouchListener(new RecyclerClickListner(MainActivity.this) {
            @Override
            public void onItemClick(RecyclerView recyclerView, View itemView, int position) {
//                Snackbar.make(parentView,
//                                              userArrayList.get(position).getFollowersUrl() + " | "
//                                            + userArrayList.get(position).getFollowingUrl()  + " | " +
//                                             userArrayList.get(position).getHtml_url() , Snackbar.LENGTH_LONG).show();

                login = userArrayList.get(position).getLogin();
                calling_2(login);
            }
        });
    }

    private void calling_2(String login) {
        ApiService api = RetrofitClient.getApiService();
        //calling json (запрос)
        Call<List<User>> call_2 = api.getFIO(login);

        //обратный вызов будет, после получения ответа
        ((retrofit2.Call) call_2).enqueue(new Callback<List<User>>() { //делаем запрос
            @Override
            public void onResponse(@NonNull retrofit2.Call<List<User>> call, @NonNull Response<List<User>> response) {
                //если успешно

                if (response.isSuccessful()) { //если успешно получили
                    userArrayList = response.body();

                    //создаем адаптер
                    adapter = new UserAdapterRecycler(MainActivity.this, userArrayList, picasso);
                    recyclerView.setAdapter(adapter);

//                    adapter.notifyDataSetChanged();

                    recyclerClick();

                } else {
                    Snackbar.make(parentView, R.string.string_some_thing_wrong, Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull retrofit2.Call<List<User>> call, @NonNull Throwable t) {
                //если не успешно

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // получим идентификатор выбранного пункта меню
        int id = item.getItemId();

        UserAdapterRecycler adapter= new UserAdapterRecycler(MainActivity.this, userArrayList, picasso);

        // Операции для выбранного пункта меню
        switch (id) {
            case R.id.action_button_picasso:
                picasso = true;
                return true;
            case R.id.action_button_glide:
                picasso = false;
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

