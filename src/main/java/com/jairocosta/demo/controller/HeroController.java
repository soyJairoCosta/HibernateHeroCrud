package com.jairocosta.demo.controller;

import com.jairocosta.demo.model.Hero;
import com.jairocosta.demo.repository.HeroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/heroes")
public class HeroController {

    @Autowired
    HeroRepository heroRepository;

    // Get All Hero
    // GET METHOD : http://localhost:8080/heroes/
    @GetMapping("/")
    public List<Hero> getAllNotes() {
        return heroRepository.findAll();
    }

    // Create a new Hero
    //POST METHOD : http://localhost:8080/heroes/
    //{"name": "Batman", "description": "Cool guy", "alin": "Good", "city": "Gotham"}
    @PostMapping("/")
    public Hero createHero(@Valid @RequestBody Hero hero) {
        return heroRepository.save(hero);
    }

    // Get a Single Hero
    //GET METHOD: http://localhost:8080/heroes/id?id=1
    @GetMapping("/id")
    public ResponseEntity<Hero> getHeroById(@RequestParam(name = "id")  Long heroId) {
        ResponseEntity response = Optional.of(this.heroRepository.findById(heroId))
                .filter(it -> it != null)
                .map(it-> new ResponseEntity<>(it,HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
        return response;

    }

    // Check alin of the Hero
    // GET METHOD: http://localhost:8080/heroes/alin?alin=bueno
    @GetMapping("/alin")
    public ResponseEntity<List<Hero>> gtHeroByAlin(@RequestParam(name = "alin") String alin) {
        ResponseEntity response = Optional.of(this.heroRepository.findHeroByAlin(alin))
                .filter(it -> !it.isEmpty())
                .map(it-> new ResponseEntity<>(it,HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
        return response;

    }

    // Update a Hero
    // PUT METHOD : http://localhost:8080/heroes/update?id=1
    // {"id": "1", "name": "Superman", "description": "Nice guy", "alin": "Good", "city": "SmallVille"}
    @PutMapping("/update")
    public Hero updateHero(@Valid @RequestBody Hero heroDetails) {
        Hero updatedHero = heroRepository.save(heroDetails);
        return updatedHero;
    }

    // Delete a Hero
    //METHOD DELETE: http://localhost:8080/heroes/delete?id=3
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteHero(@RequestParam(name = "id") Long heroId) {
        ResponseEntity response = this.heroRepository.findById(heroId)
                .filter(it-> it != null)
                .map( it -> {
                    heroRepository.delete(it);
                    return new ResponseEntity<>(true, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));

        return response;
    }
}