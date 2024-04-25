package com.student.teacher.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.teacher.project.entity.Teacher;
import com.teacher.project.exception.ResourceNotFoundException;
import com.teacher.project.repository.TeacherRepository;

@RestController
@RequestMapping("/api/users")
public class TeacherController {

	@Autowired
	private TeacherRepository teacherRepository;

	// get all users
	@GetMapping
	public List<Teacher> getAllTeachers() {
		return this.teacherRepository.findAll();
	}

	// get user by id
	@GetMapping("/{id}")
	public Teacher getUserById(@PathVariable (value = "id") long userId) {
		return this.teacherRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id :" + userId));
	}

	// create user
	@PostMapping
	public Teacher createTeacher(@RequestBody Teacher user) {
		return this.teacherRepository.save(user);
	}
	
	// update user
	@PutMapping("/{id}")
	public Teacher updateTeacher(@RequestBody Teacher user, @PathVariable ("id") long userId) {
		 Teacher existingUser = this.teacherRepository.findById(userId)
			.orElseThrow(() -> new ResourceNotFoundException("User not found with id :" + userId));
		 existingUser.setFirstName(user.getFirstName());
		 existingUser.setLastName(user.getLastName());
		 existingUser.setEmail(user.getEmail());
		 return this.teacherRepository.save(existingUser);
	}
	
	// delete user by id
	@DeleteMapping("/{id}")
	public ResponseEntity<Teacher> deleteTeacher(@PathVariable ("id") long userId){
		 Teacher existingUser = this.teacherRepository.findById(userId)
					.orElseThrow(() -> new ResourceNotFoundException("User not found with id :" + userId));
		 this.teacherRepository.delete(existingUser);
		 return ResponseEntity.ok().build();
	}
}
