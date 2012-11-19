package com.emo.sample.domain.account;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JournalRepository extends JpaRepository<Journal, Long>{

	public Journal findByForAccountName(final String forAccountName);
}
