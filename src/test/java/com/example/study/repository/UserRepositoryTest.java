package com.example.study.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.study.StudyApplication;
import com.example.study.model.entity.User;
import com.example.study.ropository.UserRepository;

public class UserRepositoryTest extends StudyApplication {

	
	@Autowired
	private UserRepository userRepository;
	
	@Test
	public void create() {
		User user = new User();
		user.setAccount("test02");
		user.setEmail("test01@gmil.com");
		user.setPhoneNumber("010-4444-1111");
		user.setCreatedAt(LocalDateTime.now());
		user.setCreatedBy("admin2");
		
		User newUser = userRepository.save(user);
		System.out.println("user : " + newUser);
		//Hibernate: insert into user (account, created_at, created_by, email, phone_number, updated_at, updated_by) values (?, ?, ?, ?, ?, ?, ?)
		//user : User(id=4, account=test02, email=test01@gmil.com, phoneNumber=010-4444-1111, createdAt=2021-05-11T22:02:15.549, createdBy=admin2, updatedAt=null, updatedBy=null)
	}
	
	@Test
	public void read() {
		Optional<User> user = userRepository.findById(2L);
		
		// user값이 있으면 출력
		user.ifPresent(selectUser->{
			System.out.println("user : " + user);
			//Hibernate: select user0_.id as id1_0_0_, user0_.account as account2_0_0_, user0_.created_at as created_3_0_0_, user0_.created_by as created_4_0_0_, user0_.email as email5_0_0_, user0_.phone_number as phone_nu6_0_0_, user0_.updated_at as updated_7_0_0_, user0_.updated_by as updated_8_0_0_ from user user0_ where user0_.id=?
			//user : Optional[User(id=2, account=test01, email=test01@gmil.com, phoneNumber=010-4444-1111, createdAt=2021-05-11T22:00:07, createdBy=admin, updatedAt=null, updatedBy=null)]
		});
	}
	
	@Test
	public void update() {
		
		// 먼저 select (이 데이터를 업데이트한다)
		Optional<User> user = userRepository.findById(2L);
		
		user.ifPresent(selectUser->{
			
			selectUser.setAccount("PPPP");
			selectUser.setUpdatedAt(LocalDateTime.now());
			selectUser.setUpdatedBy("update mehod()");
			
			userRepository.save(selectUser);
			
		});		
	}
	
	
	@Test
	@Transactional	// 실제로db에서 삭제안
	public void delete() {
		// 먼저 select (이 데이터를 업데이트한다)
		Optional<User> user = userRepository.findById(2L);
		
		user.ifPresent(selectUser->{
			userRepository.delete(selectUser);
		});		
		
	}
}
