package com.mandycode.random.friends.repository;

import com.mandycode.random.friends.model.FriendsEpisode;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface FriendsEpisodeRepository extends ReactiveMongoRepository<FriendsEpisode, String> {
}
