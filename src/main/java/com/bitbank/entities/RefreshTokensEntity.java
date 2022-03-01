package com.bitbank.entities;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "refresh_tokens")
public class RefreshTokensEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "INT(11) UNSIGNED")
	private Long id;

	@Column(name = "token", nullable = false, unique = true)
	private String token;

	@OneToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private UsersEntity usersEntity;

	@Column(nullable = false)
	private Instant expiryDate;

}
