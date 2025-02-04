package com.zuhriddin.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.zuhriddin.model.BaseModel;
import com.zuhriddin.model.Follow;
import com.zuhriddin.model.User;
import com.zuhriddin.service.util.JsonUtil;

import java.util.List;
import java.util.Map;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Collectors;

public class UserService implements BaseService<User, UUID> {

    private static final String PATH = "instagram/src/main/resources/file_resources/users.json";

    @Override
    public boolean has(List<User> list, User user) {
        return list.stream()
                .anyMatch(user1 -> user1.getPhoneNumber().equals(user.getPhoneNumber()) &&
                        user1.getEmail().equals(user.getEmail()) && user1.getUsername().equalsIgnoreCase(user.getUsername()));
    }

    public void update(User user) {
        List<User> users = read();
        int index = indexOf(users, user);
        users.set(index, user);
        write(users);
    }

    private int indexOf(List<User> users, User user) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getChatId().equals(user.getChatId())) {
                return i;
            }
        }
        return -1;
    }

    public User getUserByChatId(Long chatId){
        List<User> users = read();
        return users.stream()
                .filter(user -> user.getChatId().equals(chatId))
                .findFirst()
                .orElse(null);
    }


    public User login(Long chatId, String phoneNumber, String password) {
        User user = getUserByChatId(chatId);

        if (user != null) {
            if (user.getPhoneNumber().equals(phoneNumber) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public User getUserByUserName(String username) {
        List<User> users = read();
        return users.stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    public List<User> getUserFollowers(List<UUID> followersId) {
        List<User> users = read();
        Map<UUID, User> userMap = getUserMap(users);
        return followersId.stream()
                .map(userMap::get)
                .collect(Collectors.toList());
    }

    private Map<UUID, User> getUserMap(List<User> users) {
        return users.stream()
                .collect(Collectors.toMap(BaseModel::getId, user -> user));
    }

    @Override
    public List<User> read() {
        return JsonUtil.read(PATH, new TypeReference<>() {});
    }

    @Override
    public void write(List<User> list) {
        JsonUtil.write(PATH, list);
    }
}
