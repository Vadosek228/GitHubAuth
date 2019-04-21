package ru.vladislav_akulinin.githubauth.api;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import ru.vladislav_akulinin.githubauth.model.User;
import ru.vladislav_akulinin.githubauth.model.UserList;

public interface ApiService {

    //retrofit get annotation with our URL
    //And our netbod that will return us the List of UserList
    @GET("/users/octocat/followers")
//    Call<UserList> getMyJSON(); //метод для получения списка всех контактов
    Call<List<User>> getMyJSON();
}
