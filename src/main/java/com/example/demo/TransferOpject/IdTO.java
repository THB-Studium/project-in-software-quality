package com.example.demo.TransferOpject;

import java.util.UUID;

public class IdTO {
	private UUID id;

	
	// KONSTRUCTOR:
	
	public IdTO(UUID id) {
		super();
		this.id = id;
	}
	
	// SETTER & GETTER:
	
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
}
