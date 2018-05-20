package com.example.demo.param;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FM1001Request implements Serializable {

	@NotEmpty(message = "名字不能为空")
	private String name;

	@Length(min = 10, max = 16, message = "密码超过规定限定的长度：10-16位")
	private String pwd;
}
