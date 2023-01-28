package com.kalata.spring.login.payload.request;

import javax.validation.constraints.NotBlank;

public class LoginRequest {


	@NotBlank
  private String biometrieOrTelephone;

	@NotBlank
	private String password;



	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	public String getBiometrieOrTelephone() {
		return biometrieOrTelephone;
	}

	public void setBiometrieOrTelephone(String biometrieOrTelephone) {
		this.biometrieOrTelephone = biometrieOrTelephone;
	}

}
