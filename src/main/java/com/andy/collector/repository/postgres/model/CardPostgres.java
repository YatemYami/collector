package com.andy.collector.repository.postgres.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.Nonnull;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.andy.collector.enums.Editions;
import com.andy.collector.enums.Rarities;

import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "album", indexes = @Index(name="idx_card_type", columnList = "cardType"))
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class CardPostgres {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "card_id")
	@SequenceGenerator(name = "card_id", sequenceName = "card_id")
    @Column(name = "card_id")
	private int id;
	
	@Nonnull
	@Schema(required = true)
	@Column(name = "name")
    private String name;		//card name
    
	@Nonnull
	@Enumerated(EnumType.STRING)
	@Schema(required = true)
	@Column(name = "rarity")
	private Rarities rarity;		//card rarity
	
	@Nonnull
	@Enumerated(EnumType.STRING)
	@Schema(required = true)
	@Column(name = "edition")
	private Editions edition;		//card edition
	
	@Nonnull
	@Schema(required = true)
	@Column(name = "set")
	private String set;			//card set
	
	@Nonnull
	@Schema(required = true)
	@Column(name = "language")
	private String language;	//card language
    
	@Nonnull
	@OneToMany(cascade = {CascadeType.ALL}, orphanRemoval = true, fetch = FetchType.EAGER)
	private Collection<NotePostgres> notes = new ArrayList<>();
	
	@Nonnull
	@Schema(required = true)
	@Column(name = "cardType")
	private final String cardType = getCardType();
	
	public CardPostgres() {}
    
    public String getName() {
        return name;
    }
    
    public Rarities getRarity() {
        return rarity;
    }
    
    public Editions getEdition() {
        return edition;
    }
    
    public String getSet() {
        return set;
    }
    
    public String getLanguage() {
        return language;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setRarity(Rarities rarity) {
        this.rarity = rarity;
    }
    
    public void setEdition(Editions edition) {
        this.edition = edition;
    }
    
    public void setSet(String set) {
        this.set = set;
    }
    
    public void setLanguage(String language) {
        this.language=language;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Collection<NotePostgres> getNotes() {
		return notes;
	}

	public void setNotes(Collection<NotePostgres> notes) {
		this.notes = notes;
	}

	public abstract String getCardType();
	
    
    
}
