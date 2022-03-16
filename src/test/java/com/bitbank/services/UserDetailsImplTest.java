package com.bitbank.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bitbank.entities.UsersEntity;

class UserDetailsImplTest {

	private UserDetailsImpl user;

	private UserDetailsImpl getUser() {
		return this.user;
	}

	@BeforeEach
	public void buildUserDetailsImpl() {
		UsersEntity usersEntity = new UsersEntity(1L, "username", "password", null);
		UserDetailsImpl user = UserDetailsImpl.build(usersEntity);
		this.user = user;
	}

	@Test
	void testCanBuild() {
		assertEquals(1L, this.getUser().getId());
	}

	@Test
	void testIsEnabled() {
		assertTrue(this.getUser().isEnabled());
	}

	@Test
	void testIsAccountNonExpired() {
		assertTrue(this.getUser().isAccountNonExpired());
	}

	@Test
	void testIsAccountNonLocked() {
		assertTrue(this.getUser().isAccountNonLocked());
	}

	@Test
	void testIsCredentialsNonExpired() {
		assertTrue(this.getUser().isCredentialsNonExpired());
	}

	@Test
	void testGetAuthorities() {
		assertEquals(Collections.EMPTY_LIST, this.getUser().getAuthorities());
	}
}
