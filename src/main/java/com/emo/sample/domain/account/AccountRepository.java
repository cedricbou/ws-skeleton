package com.emo.sample.domain.account;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long>{

	public Account findByName(final String name);
}
