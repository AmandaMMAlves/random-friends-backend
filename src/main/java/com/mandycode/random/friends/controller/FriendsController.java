package com.mandycode.random.friends.controller;

import com.mandycode.random.friends.model.FriendsEpisode;
import com.mandycode.random.friends.model.FriendsEpisodeEvent;
import com.mandycode.random.friends.repository.FriendsEpisodeRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RequiredArgsConstructor
@RestController
@RequestMapping("/friends-episodes")
public class FriendsController {

    private final FriendsEpisodeRepository repository;

    @GetMapping
    public Flux<FriendsEpisode> getAllEpisodes(){
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<FriendsEpisode>> getEpisode(@PathVariable String id){
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    //TODO: Fazer um getBySeason

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<FriendsEpisode> saveFriendsEpisode(@RequestBody FriendsEpisode episode){
        return repository.save(episode);
    }

    @PutMapping("{id}")
    public Mono<ResponseEntity<FriendsEpisode>> updateEpisode(@PathVariable(value = "id") String id,
                                                              @RequestBody FriendsEpisode episode){
        return repository.findById(id)
                .filter(existingEpisode -> checkChangeOnExistingEpisode(existingEpisode, episode))
                .flatMap(existingEpisode -> {
                    existingEpisode.setFoto(episode.getFoto());
                    existingEpisode.setSinopse(episode.getSinopse());
                    return repository.save(existingEpisode);
                })
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    private boolean checkChangeOnExistingEpisode(FriendsEpisode existingEpisode, FriendsEpisode updatedEpisode){
        return StringUtils.equals(existingEpisode.getEpisodio(), updatedEpisode.getEpisodio())
                && StringUtils.equals(existingEpisode.getTemporada(), updatedEpisode.getTemporada());
    }

    @DeleteMapping("{id}")
    public Mono<ResponseEntity<Void>> deleteEpisode(@PathVariable(value = "id") String id){
        return repository.findById(id)
                .flatMap(episode -> repository.delete(episode)
                        .then(Mono.just(ResponseEntity.ok().<Void>build()))
                ).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping
    public Mono<Void> deleteAllEpisodes() {
        return repository.deleteAll();
    }

    @GetMapping(value = "/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<FriendsEpisodeEvent> getFriendsEpisodeEvents(){
        return Flux.interval(Duration.ofSeconds(5))
                .map(val -> new FriendsEpisodeEvent(val, "Product Event"));
    }

}
