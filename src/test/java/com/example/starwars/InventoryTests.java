package com.example.starwars;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.starwars.model.entity.Inventory;
import com.example.starwars.model.entity.InventoryItem;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class InventoryTests {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Test
	public void testInventoryPoints() {
		logger.info("testInventory Test");
		int weapons = 3;
		int ammos = 50;
		int waters = 3;
		int foods = 2;
		Inventory inventory = new Inventory()
			.item(InventoryItem.WEAPON, weapons)
			.item(InventoryItem.AMMO, ammos)
			.item(InventoryItem.WATER, waters)
			.item(InventoryItem.FOOD, foods);
		Assertions.assertEquals(
			InventoryItem.WEAPON.getPoints()*weapons + 
				InventoryItem.AMMO.getPoints()*ammos + 
				InventoryItem.WATER.getPoints()*waters +
				InventoryItem.FOOD.getPoints()*foods
			, inventory.totalPoints());
	}

	@Test
	public void testInventoryAdd() {
		logger.info("testInventoryAdd Test");
		Inventory inventory = new Inventory();
		Assertions.assertTrue(inventory.addItemIfValid(InventoryItem.WATER, 3));
		Assertions.assertTrue(inventory.addItemIfValid(InventoryItem.WATER, 0));
		Assertions.assertFalse(inventory.addItemIfValid(InventoryItem.WATER, -4));
		Assertions.assertTrue(inventory.addItemIfValid(InventoryItem.WATER, 2));
		Assertions.assertTrue(inventory.addItemIfValid(InventoryItem.FOOD, 0));
		Assertions.assertTrue(inventory.addItemIfValid(InventoryItem.AMMO, 2));
		Assertions.assertTrue(inventory.addItemIfValid(InventoryItem.AMMO, 1));
		Assertions.assertEquals(5, inventory.items(InventoryItem.WATER));
		Assertions.assertEquals(0, inventory.items(InventoryItem.FOOD));
		Assertions.assertEquals(3, inventory.items(InventoryItem.AMMO));
		Assertions.assertEquals(0, inventory.items(InventoryItem.WEAPON));
		Assertions.assertEquals(2, inventory.size());
		Assertions.assertEquals(2, inventory.stream().count());
		Assertions.assertTrue(inventory.hasItem(InventoryItem.AMMO, 0));
		Assertions.assertTrue(inventory.hasItem(InventoryItem.AMMO, 1));
		Assertions.assertTrue(inventory.hasItem(InventoryItem.AMMO, 2));
		Assertions.assertTrue(inventory.hasItem(InventoryItem.AMMO, 3));
		Assertions.assertFalse(inventory.hasItem(InventoryItem.AMMO, 4));
		Assertions.assertTrue(inventory.hasItem(InventoryItem.WEAPON, 0));
		inventory.addItemNoCheck(InventoryItem.WATER, -10);
		Assertions.assertEquals(0, inventory.items(InventoryItem.WATER));
		Assertions.assertEquals(1, inventory.size());
		Assertions.assertEquals(1, inventory.stream().count());
		Assertions.assertTrue(inventory.addItemIfValid(InventoryItem.AMMO, -3));
		Assertions.assertEquals(0, inventory.items(InventoryItem.AMMO));
		Assertions.assertEquals(0, inventory.size());
		Assertions.assertEquals(0, inventory.stream().count());
	}

}

