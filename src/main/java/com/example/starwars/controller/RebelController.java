package com.example.starwars.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.starwars.exception.RebelException;
import com.example.starwars.model.entity.Location;
import com.example.starwars.model.entity.api.ItemsTrade;
import com.example.starwars.model.entity.api.RebelApi;
import com.example.starwars.service.RebelService;

@RestController
@RequestMapping(value="rebel")
public class RebelController {

	@Autowired
	private RebelService rebelService;

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<RebelApi> find(Pageable pageable) {
		return Collections.emptyList();
	}

	@RequestMapping(path = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody RebelApi findById(@PathVariable Integer id) {
		return rebelService.transformToRebelApi(rebelService.getRebelFromId(id));
	}

	@RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.CREATED)
	public @ResponseBody RebelApi create(@RequestBody RebelApi rebelApi) {
		return rebelService.transformToRebelApi(rebelService.createRebelFromApi(rebelApi));
	}

	@RequestMapping(path = "{id}/location", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Void> updateLocaltion(@PathVariable Integer id, @RequestBody Location location) {
		rebelService.updateLocation(id, location);
		return ResponseEntity.ok().build();
	}

	@RequestMapping(path = "{id}/traitor/{traitorId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Void> reportBetrayal(@PathVariable Integer id, @PathVariable Integer traitorId) throws RebelException {
		rebelService.indicateTraitor(id, traitorId);
		return ResponseEntity.ok().build();
	}

	@RequestMapping(path = "{id1}/trade/{id2}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Void> trade(@PathVariable Integer id1, @PathVariable Integer id2, @RequestBody ItemsTrade itemsTrade) throws RebelException {
		rebelService.tradeItems(id1, itemsTrade.getItemsOffer(), id2, itemsTrade.getItemsReceive());
		return ResponseEntity.ok().build();
	}
}
