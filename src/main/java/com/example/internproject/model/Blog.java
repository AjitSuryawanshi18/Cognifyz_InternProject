//blog step 1 

package com.example.internproject.model;


import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="Blogs")
public class Blog {
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;
 private String title;
 
 // Use @Lob to indicate a large object (CLOB)
 @Lob
 @Column(name = "content", columnDefinition = "TEXT")
 private String content;

 private LocalDateTime createdAt;
 
 //for connecting user to its blogs
 @ManyToOne
 @JoinColumn(name = "user_id", nullable = false)
 private MyUser author;

 
public Long getId() {
	return id;
}
public void setId(Long id) {
	this.id = id;
}
public String getTitle() {
	return title;
}
public void setTitle(String title) {
	this.title = title;
}
public String getContent() {
	return content;
}
public void setContent(String content) {
	this.content = content;
}
public LocalDateTime getCreatedAt() {
	return createdAt;
}
public void setCreatedAt(LocalDateTime createdAt) {
	this.createdAt = createdAt;
}
public MyUser getAuthor() {
	return author;
}
public void setAuthor(MyUser author) {
	this.author = author;
}

 
 

}

