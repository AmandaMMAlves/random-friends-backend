package com.mandycode.random.friends.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FriendsEpisodeEvent {
    private Long eventId;
    private String eventType;
}
