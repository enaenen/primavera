package com.genius.primavera.application;

import com.genius.primavera.domain.model.user.User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class HelloServiceImpl implements HelloService {

	@Override
	public List<User> getUsers() {
		return Collections.EMPTY_LIST;
	}
}