package com.bitbank.test.services.v1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bitbank.entities.v1.UsersEntity;
import com.bitbank.services.v1.UserDetailsImpl;

class UserDetailsImplTest {

	private UserDetailsImpl user;

	private UserDetailsImpl getUser() {
		return this.user;
	}

	@BeforeEach
	public void buildUserDetailsImpl() {
		UsersEntity usersEntity = new UsersEntity(1L, "username", "password");
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
