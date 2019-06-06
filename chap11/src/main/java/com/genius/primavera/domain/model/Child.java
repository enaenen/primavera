package com.genius.primavera.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Child {
	private long id;
	private long parentId;
	private int age;
	private String name;
}