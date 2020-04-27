package com.yosep.msa.account;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class YoggaebiUserDTO {
	@NotBlank
	private String userName;
	
	@NotBlank
	private String password;
	
	@NotBlank
	private String name;
	
	@NotBlank
	@Email
	private String email;
	
	@NotBlank
	private String phone;
	
	@NotNull
	private String postCode;
	
	@NotNull
	private String roadAddr;
	
	@NotNull
	private String jibunAddr;
	
	@NotNull
	private String extraAddr;
	
	@NotNull
	private String detailAddr;
}
