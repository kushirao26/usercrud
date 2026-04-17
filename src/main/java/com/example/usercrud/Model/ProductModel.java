package com.example.usercrud.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Document(collection = "products")
	public class ProductModel {
	
	@Id
	private String id;
	private String name;
	private double price;

	 }

