package com.andy.collector.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.andy.collector.model.Card;
import com.andy.collector.model.MonsterCard;
import com.andy.collector.model.SpellCard;
import com.andy.collector.model.TrapCard;
import com.andy.collector.repository.CardRepository;

import io.swagger.v3.oas.annotations.Hidden;


@RestController
@RequestMapping("/collector")
public class CardController {
		private final CardRepository cardRepository;
		
	CardController(@Autowired CardRepository cardRepository){
		this.cardRepository = cardRepository;
	}
	
	@PostMapping(value = "/add-monster", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MonsterCard> addCard(@RequestBody MonsterCard monsterCard) throws SQLException {
		boolean cardNew = cardRepository.addCard(monsterCard);
		
		if(cardNew) {
			return new ResponseEntity<MonsterCard>(monsterCard, HttpStatus.OK);
		} else {
			return new ResponseEntity<MonsterCard>(HttpStatus.CONFLICT);
		}
		
	}
	
	@PutMapping(value = "/edit-monster/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MonsterCard> updateCard(@PathVariable("id") String id, @RequestBody MonsterCard monsterCard) throws SQLException {

		boolean cardNew = cardRepository.editCard(monsterCard, id);
		
		if(cardNew) {
			return new ResponseEntity<MonsterCard>(monsterCard, HttpStatus.OK);
		} else {
			return new ResponseEntity<MonsterCard>(HttpStatus.NOT_FOUND);
		}
	}
	
	@Hidden
	@PostMapping(value = "/add-spell", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Card> addSpellCard(@RequestBody SpellCard spellCard) throws SQLException {
		boolean cardNew = cardRepository.addCard(spellCard);
		
		if(cardNew) {
			return new ResponseEntity<Card>(spellCard, HttpStatus.OK);
		} else {
			return new ResponseEntity<Card>(HttpStatus.CONFLICT);
		}
		
	}
	
	@PostMapping(value = "/add-trap", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Card> addTrapCard(@RequestBody TrapCard trapCard) throws SQLException {
		boolean cardNew = cardRepository.addCard(trapCard);
		
		if(cardNew) {
			return new ResponseEntity<Card>(trapCard, HttpStatus.OK);
		} else {
			return new ResponseEntity<Card>(HttpStatus.CONFLICT);
		}
		
	}
	
	@PutMapping(value = "/edit-card/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Card> updateCard(@PathVariable("id") String id, @RequestBody Card card) throws SQLException {

		boolean cardNew = cardRepository.editCard(card, id);
		
		if(cardNew) {
			return new ResponseEntity<Card>(card, HttpStatus.OK);
		} else {
			return new ResponseEntity<Card>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping(value = "/card-monster-details/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MonsterCard> loadMonsterCardDetails(@PathVariable("id") String id) {
		MonsterCard monsterCard = null;
				
		try {
			monsterCard = cardRepository.loadMonsterCardDetails(id);
			return new ResponseEntity<MonsterCard>(monsterCard, HttpStatus.OK);
			
		} catch (SQLException ex) {
			return new ResponseEntity<MonsterCard>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> deleteCard(@PathVariable("id") String id){
		try {
			cardRepository.deleteCard(id);
			return new ResponseEntity<String>("Card deleted.",HttpStatus.OK);
		} catch (SQLException ex) {
			return new ResponseEntity<String>("Card not found", HttpStatus.NOT_FOUND);
		}
	}
	
	
	
	@GetMapping(value = "/show-all-cards", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Card>> loadAllCards() throws SQLException {
		List<Card> cards = cardRepository.showAllCards();
		
		if(cards != null) {
			return new ResponseEntity<List<Card>> (cards, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<Card>> (HttpStatus.NOT_FOUND);
		}
		
	}
	
	
}