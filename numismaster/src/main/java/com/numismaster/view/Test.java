package com.numismaster.view;

import java.util.List;

import com.numismaster.model.User;
import com.numismaster.repository.UserRepository;

public class Test {

	public static void main(String[] args) {
		UserRepository ur = new UserRepository();
		
		List<User> user = ur.findByName("Doe");
		
		for (int i = 0; i < user.size(); i++) {
			System.out.println(user.get(i).getFirstName() + " " + user.get(i).getLastName());
		}
	}
}
