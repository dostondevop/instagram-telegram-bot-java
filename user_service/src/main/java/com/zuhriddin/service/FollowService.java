package com.zuhriddin.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.zuhriddin.model.Follow;
import com.zuhriddin.service.util.JsonUtil;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class FollowService implements BaseService<Follow, UUID> {

    private static final String PATH = "instagram/src/main/resources/file_resources/follows.json";

    @Override
    public boolean has(List<Follow> list, Follow follow) {
        return list.stream()
                .anyMatch(follow1 -> follow1.getFollowingId().equals(follow.getFollowingId()) &&
                        follow1.getFollowerId().equals(follow.getFollowerId()));
    }

    public boolean has(UUID followerId, UUID followingId) {
        List<Follow> list = read();
        return list.stream()
                .anyMatch(follow -> follow.getFollowerId().equals(followerId) && follow.getFollowingId().equals(followingId));
    }

    public List<UUID> getFollowers(UUID id) {
        return read().stream()
                .filter(n -> n.getFollowingId().equals(id))
                .map(Follow::getFollowerId)
                .collect(Collectors.toList());
    }

    public int countOfFollowers(UUID userId) {
        return getFollowers(userId).size();
    }

    public Map<UUID, Follow> getFollowings(UUID id) {
        return read().stream()
                .filter(n -> n.getFollowerId().equals(id))
                .collect(Collectors.toMap(Follow::getFollowingId, i -> i));
    }

    public List<UUID> getFollowingsList(UUID id) {
        return read().stream()
                .filter(n -> n.getFollowerId().equals(id))
                .map(Follow::getFollowingId)
                .collect(Collectors.toList());
    }

    public int countOfFollowings(UUID userId) {
        return getFollowings(userId).size();
    }

    public Follow getFollow(UUID followerId, UUID followingId) {
        List<Follow> follows = read();

        return follows.stream()
                .filter(follow -> follow.getFollowerId().equals(followerId)
                        && follow.getFollowingId().equals(followingId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Follow> read() {
        return JsonUtil.read(PATH, new TypeReference<List<Follow>>() {});
    }

    @Override
    public void write(List<Follow> list) {
        JsonUtil.write(PATH, list);
    }
}
