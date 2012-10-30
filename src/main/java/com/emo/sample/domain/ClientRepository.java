package com.emo.sample.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, String> {
	
	public Client findByClientCode(final String ClientCode);
}
