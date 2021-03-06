package com.bitbank.services;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.bitbank.entities.UsersEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {

	private static final long serialVersionUID = -20220213165200L;
	private Long id;
	private String username;
	@JsonIgnore
	private String password;
	private Collection<? extends GrantedAuthority> authorities;

	public static UserDetailsImpl build(UsersEntity usersEntity) {

		/**
		 * The list must contain a type that extends GrantedAuthorithy:
		 * SimpleGrantedAuthority
		 */
		List<? extends GrantedAuthority> authorities = usersEntity.getRolesEntity().stream()
				.map(role -> new SimpleGrantedAuthority(role.getRole().name())).toList();

		return new UserDetailsImpl(usersEntity.getId(), usersEntity.getUsername(), usersEntity.getPassword(),
				authorities);
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}
}