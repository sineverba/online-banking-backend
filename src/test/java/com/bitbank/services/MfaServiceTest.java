package com.bitbank.services;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MfaServiceTest {

	@Test
	void testCanReturnFalseIfMfaCodeIsInvalid() {
		MfaService mfaService = new MfaService();
		assertFalse(mfaService.verify("1234", "4321"));
	}

}
