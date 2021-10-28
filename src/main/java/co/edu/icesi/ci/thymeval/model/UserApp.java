package co.edu.icesi.ci.thymeval.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Entity
@Data
public class UserApp {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private long id;
	
	@NotBlank(groups = {AdvancedInfo.class, editInfo.class})
	@Size(min=2, groups = {AdvancedInfo.class, editInfo.class})
	private String name;
	
	@NotBlank(groups = {AdvancedInfo.class, editInfo.class})
	@Email(message = "Email is not valid", groups = {AdvancedInfo.class, editInfo.class})
	private String email;
	
	@NotNull(groups = {AdvancedInfo.class, editInfo.class})
	private UserType type;
	
	@Past(groups = {BasicInfo.class, editInfo.class})
	@NotNull(groups = {BasicInfo.class, editInfo.class})
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate birthDate;
	
	@NotNull(groups = {AdvancedInfo.class, editInfo.class})
	private UserGender gender;
	
	@NotBlank(groups = {BasicInfo.class, editInfo.class})
	@Size(min = 3, max = 20, groups = {BasicInfo.class, editInfo.class})
	private String username;
	
	@NotBlank(groups = {BasicInfo.class})
	@Size(min = 8, groups = {BasicInfo.class})
	private String password;
	

	
//	@OneToMany
//	private List<Appointment> appointments;
}
