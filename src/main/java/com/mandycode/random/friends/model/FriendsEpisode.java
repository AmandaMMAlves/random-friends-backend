package com.mandycode.random.friends.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class FriendsEpisode {

    @Id
    private String id;

    private String temporada;
    private String episodio;
    private String titulo;
    private String sinopse;
    private String foto;
}
