/*
 * Copyright (c) ...
 */
package com.andy.collector.model;

import com.andy.collector.enums.Editions;
import com.andy.collector.enums.Rarities;

import io.swagger.v3.oas.annotations.media.Schema;
/**
 * The MonsterCard class provides methods for adding a new monster card to database,
 * edit data for monster card in database and to get data for monster card from the database.
 * 
 * @version		0.1 14. May 2020
 * @author 		Andrej Marcan
 */

@Schema(
		type = "object",
		title = "MonsterCard"
		)
public class MonsterCard extends Card {
	
	@Schema(required = true)
    private String summMethod;	//summoning method
    
	@Schema(required = true)
	private String attribute;	//monster attribute
	
	@Schema(required = true)
	private String atk;			//attack points
    
	@Schema(required = true)
	private String def;			//defense points
    
	@Schema(required = true)
	private String level;		//monster level
    private final String CARD_TYPE = "monster card";
    
    public MonsterCard() {};
    
    /* Constructor for MonsterCard class */
    public MonsterCard (String name, Rarities rarity, Editions edition, String set, String language,
    					String type, String summMethod, String attribute, String level,
    					String atk, String def) {
        super(name, rarity, edition, set, language, type);
        this.summMethod = summMethod;
        this.attribute = attribute;
        this.level = level;
        this.atk = atk;
        this.def = def;   
    }
    
    /* Method shows informations about monster card in console */
    @Override
    public void getInfo() {
        System.out.println(super.getName());
        System.out.println(super.getRarity());
        System.out.println(super.getEdition());
        System.out.println(super.getSet());
        System.out.println(super.getLanguage());
        System.out.println(attribute);
        System.out.println(level);
        System.out.println(atk);
        System.out.println(def);    
    }
    @Override
    public String getCardType() {
    	return CARD_TYPE;
    }
    
    public void setLevel(String level) {
        this.level = level;
    }
    
    public void setAttribute(String atribute) {
        this.attribute = atribute;
    }
    
    public void setAtk(String atk) {
        this.atk = atk;
    }
    
    public void setDef(String def) {
        this.def = def;
    }
    
    public void setSummMethod(String summMethod) {
        this.summMethod = summMethod;
    }
    
    public String getAttribute() {
        return attribute;
    }
    
    public String getLevel() {
        return level;
    }
    
    public String getAtk() {
        return atk;
    }
    
    public String getDef() {
        return def;
    }
    
    public String getSummMethod() {
        return summMethod;
    }
    
    
    
}



















