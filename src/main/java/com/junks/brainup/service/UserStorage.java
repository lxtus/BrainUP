package com.junks.brainup.service;

import com.junks.brainup.domain.User;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;

public class UserStorage {
    private List<User> users;

    private UserStorage() {
        users = new ArrayList<>();
    }

    public void addUser(User toFillUser) {
        users.add(toFillUser);
    }

    private static final class UserStorageHolder {
        private static final UserStorage instance = new UserStorage();
    }

    public static UserStorage getInstance() {
        return UserStorageHolder.instance;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void saveUsersToJson(String filename) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try (FileWriter writer = new FileWriter(filename)) {
            List<User> usersToSave = UserStorage.getInstance().getUsers();
            Type userListType = new TypeToken<ArrayList<User>>() {
            }.getType();
            gson.toJson(usersToSave, userListType, writer);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadUsersFromJson(String filename) {
        Gson gson = new Gson();

        try (FileReader reader = new FileReader(filename)) {
            Type userListType = new TypeToken<ArrayList<User>>() {
            }.getType();
            ArrayList<User> loadedUsers = gson.fromJson(reader, userListType);

            List<User> existingUsers = UserStorage.getInstance().getUsers();
            if (existingUsers == null) {
                existingUsers = new ArrayList<>();
            }
            existingUsers.addAll(loadedUsers);
            UserStorage.getInstance().setUsers(existingUsers);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

