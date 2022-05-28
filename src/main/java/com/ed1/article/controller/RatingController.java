package com.ed1.article.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ed1.article.model.Rating;
import com.ed1.article.service.RatingService;
import com.ed1.article.structures.List.DoubleLinkedList;

@RestController
@RequestMapping("/ratings")
public class RatingController {
	
	@Autowired
	private RatingService ratingService;

	@GetMapping
	public ResponseEntity<DoubleLinkedList<Rating>> findAll(){
		DoubleLinkedList<Rating> ratingList = ratingService.findAll();
		return ResponseEntity.ok().body(ratingList);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Rating> findById(@PathVariable Long id) {
		Rating ratingDto = ratingService.findById(id);
		return ResponseEntity.ok().body(ratingDto);
	}
	
	@PostMapping
	public ResponseEntity<Rating> save(@RequestBody Rating rating) {
		Rating ratingDto = ratingService.save(rating);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(ratingDto.getId()).toUri();
		return ResponseEntity.created(uri).body(ratingDto);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Rating> update(@PathVariable Long id, @RequestBody Rating rating) {
		Rating ratingDto = ratingService.update(id, rating);
		return ResponseEntity.ok().body(ratingDto);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteRating(@PathVariable Long id) {
		ratingService.deleteRating(id);
		return ResponseEntity.noContent().build();
	}
}
