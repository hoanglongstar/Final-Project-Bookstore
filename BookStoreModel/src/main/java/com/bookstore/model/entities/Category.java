package com.bookstore.model.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

@Entity
@Table(name = "categories")
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "name")
	@Size(min = 4, max = 30, message = "Please enter category name")
	private String name;
	
	@Column(name = "code")
	@Size(min = 4, max = 10, message = "Please enter category code")
	private String code;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "photo")
	private String photo;
	
	@Column(name = "enabled")
	private Boolean enabled;
	
	@OneToOne
	@JoinColumn(name = "parent_id")
	private Category parent;
	
	@OneToMany(mappedBy = "parent")
	private Set<Category> subCategory = new HashSet<>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Category getParent() {
		return parent;
	}

	public void setParent(Category parent) {
		this.parent = parent;
	}

	public Set<Category> getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(Set<Category> subCategory) {
		this.subCategory = subCategory;
	}
	
	@Transient
	public String getPhotoPath() {
		if(photo == null || photo.equals("")) {
			return "../images/category.png";
		}
		if(id != null & photo != null) {
			return "http://localhost:8081/files/" + photo;
		}
		return null;
	}
	
}
