package com.andy.collector.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.andy.collector.dto.CardDTO;
import com.andy.collector.dto.NoteDTO;
import com.andy.collector.dto.SpellCardDTO;
import com.andy.collector.enums.Editions;
import com.andy.collector.enums.Rarities;

@TestMethodOrder(OrderAnnotation.class)
@Testcontainers
@Profile("test")
@SpringBootTest
@ContextConfiguration(initializers = NoteServiceMongoTest.Initializer.class)
public class NoteServiceMongoTest {
	protected static NoteDTO note1;
	protected static NoteDTO note2;
	protected static SpellCardDTO spell;
	
	@Autowired
	protected NoteService noteService;
	
	@Autowired
	protected CardService cardService;
	
	@Container
	public static MongoDBContainer mongoDBContainer = new MongoDBContainer().withExposedPorts(27018);
	
	static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
		@Override
	    public void initialize(ConfigurableApplicationContext context) {
			TestPropertyValues.of("spring.data.mongodb.uri=" + mongoDBContainer.getReplicaSetUrl())
			.applyTo(context.getEnvironment());
	        TestPropertyValues.of("spring.data.mongodb.host=" + mongoDBContainer.getHost())
	        			.applyTo(context.getEnvironment());	        
	        TestPropertyValues.of("andy.database.picker=mongo").applyTo(context.getEnvironment());
    }}
	
	@BeforeAll
	public static void init() {
		note1 = new NoteDTO();
		note1.setId(1);
		note1.setNote("Hello from note 1");
		
		note2 = new NoteDTO();
		note2.setId(2);
		note2.setNote("Hello from note 2");
		
		spell = new SpellCardDTO();
		spell.setId(1);
		spell.setName("TestSpell");
		spell.setEdition(Editions.UE);
		spell.setRarity(Rarities.COM);
		spell.setSet("DDS");
		spell.setLanguage("English");
		spell.setType("Field Spell Card");
		spell.setNotes(new ArrayList<NoteDTO>());
	}
	
	@Test
	@Order(1)
	public void addNoteToCardTest() {
		cardService.addNewCard(spell);
		
		noteService.addNoteToCard(note1, 1);
		noteService.addNoteToCard(note2, 1);
		
		NoteDTO noteGOT = noteService.showNote(1);
		assertEquals(note1.getNote(), noteGOT.getNote());
		
		NoteDTO noteGOT2 = noteService.showNote(2);
		assertEquals(note2.getNote(), noteGOT2.getNote());
	}
	
	@Test
	@Order(2)
	public void editNoteByCardIdTest() {
		note1.setNote("HOLA from note 1");
		
		noteService.editNoteByIdCard(note1, 1, 1);
		
		NoteDTO noteGOT = noteService.showNote(1);
		assertEquals(note1.getNote(), noteGOT.getNote());
	}
	
	@Test
	@Order(3)
	public void deleteNoteFromCard() {
		noteService.deleteNoteById(1, 2);
		
		CardDTO card = cardService.getAllCards().get(0);
		List<NoteDTO> notes = (List<NoteDTO>) card.getNotes();
		assertTrue(notes.size()==1);
	}
	
	@Test
	@Order(4)
	public void deleteAllNotesFromCard() {
		noteService.deleteAllNotesFromCard(1);
		
		CardDTO card = cardService.getAllCards().get(0);
		List<NoteDTO> notes = (List<NoteDTO>) card.getNotes();
		assertTrue(notes.size()==0);
	}
}
