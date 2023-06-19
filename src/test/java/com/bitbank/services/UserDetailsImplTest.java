package com.bitbank.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.bitbank.constants.ERole;
import com.bitbank.entities.RolesEntity;
import com.bitbank.entities.UsersEntity;

class UserDetailsImplTest {

	private UserDetailsImpl user;

	private UserDetailsImpl getUser() {
		return this.user;
	}

	@BeforeEach
	public void buildUserDetailsImpl() {
		// ADMIN - Initialize the set
		Set<RolesEntity> adminRole = new HashSet<>();
		// ADMIN - Generate the entity
		RolesEntity adminRolesEntity = validRolesEntity(1L, ERole.valueOf("ROLE_ADMIN"));
		// ADMIN - Add the entity to the set
		adminRole.add(adminRolesEntity);
		UsersEntity usersEntity = new UsersEntity(1L, "username", "password", "1111", "ABCDE", adminRole);
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
		assertEquals(Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")),
				this.getUser().getAuthorities());
	}

	/**
	 * Generate a RolesEntity
	 */
	private static RolesEntity validRolesEntity(Long id, ERole role) {
		return RolesEntity.builder().id(id).role(role).build();
	}
}
