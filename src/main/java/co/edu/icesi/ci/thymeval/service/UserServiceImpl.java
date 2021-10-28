package co.edu.icesi.ci.thymeval.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.icesi.ci.thymeval.model.UserApp;
import co.edu.icesi.ci.thymeval.model.UserType;
import co.edu.icesi.ci.thymeval.model.UserGender;
import co.edu.icesi.ci.thymeval.repository.UserRepository;
import co.edu.icesi.ci.thymeval.service.interfaces.UserService;

@Service
public class UserServiceImpl implements UserService{

	private UserRepository userRepository;

	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public UserApp save(UserApp user) {
		user.setPassword("{noop}"+ user.getPassword());
		return userRepository.save(user);

	}

	public Optional<UserApp> findById(long id) {

		return userRepository.findById(id);
	}

	public Iterable<UserApp> findAll() {
		return userRepository.findAll();
	}
	
	public Iterable<UserApp> findAllPatients() {
		return userRepository.findByType(UserType.patient);
	}
	
	public Iterable<UserApp> findAllDoctors() {
		return userRepository.findByType(UserType.doctor);
	}


	public void delete(UserApp user) {
		userRepository.delete(user);

	}

	public UserGender[] getGenders() {
		
		return UserGender.values();
	}

	public UserType[] getTypes() {
		// TODO Auto-generated method stub
		return UserType.values();
	}
	
	public void updateUserForSave(UserApp user) {
		UserApp modUser = userRepository.findById(user.getId()).get();
		//modUser.setBirthDate(user.getBirthDate());
		modUser.setEmail(user.getEmail());
		modUser.setGender(user.getGender());
		modUser.setName(user.getName());
		modUser.setType(user.getType());
		
		//modUser.setUsername(user.getUsername());
		
		/*if(user.getPassword() != null) {
			modUser.setPassword(user.getPassword());
		}*/
		userRepository.save(modUser);
	}
	
	public void update(UserApp user) {
		UserApp modUser = userRepository.findById(user.getId()).get();
		modUser.setBirthDate(user.getBirthDate());
		modUser.setEmail(user.getEmail());
		modUser.setGender(user.getGender());
		modUser.setName(user.getName());
		modUser.setType(user.getType());
		//modUser.setUsername(user.getUsername());
		
		if(!user.getPassword().equals("")) {
			modUser.setPassword(user.getPassword());
		}
		userRepository.save(modUser);
	}
}
