package com.example.starwars.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.starwars.model.entity.InventoryReport;
import com.example.starwars.service.RebelService;

@RestController
@RequestMapping(value="rebel/report")
public class RebelReportController {

	@Autowired
	private RebelService rebelService;

	@RequestMapping(path = "/traitorsPercent", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Float traitorsPercent() {
		return rebelService.getTraitorPercent();
	}

	@RequestMapping(path = "/rebelsPercent", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Float rebelsPercent() {
		return rebelService.getRebelPercent();
	}

	@RequestMapping(path = "/inventoryAverage", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody InventoryReport inventoryAverage() {
		return rebelService.getInventoryAverage();
	}

	@RequestMapping(path = "/traitorPoints", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Long traitorPoints() {
		return rebelService.getTraitorPoints();
	}
}
