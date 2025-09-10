package com.Coorg.BlogPost.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
//import com.Coorg.BlogPost.model.Blog;

import java.util.Date;



public class BlogDto {
    private Long id;
    private String title;
    private String content;
    private String authorName; // Only send the author's name
    private Date createdAt;
    
    
	@Override
	public String toString() {
		return "BlogDto [id=" + id + ", title=" + title + ", content=" + content + ", authorName=" + authorName
				+ ", createdAt=" + createdAt + "]";
	}
	public BlogDto() {
		super();
	}
	public BlogDto(Long id, String title, String content, String authorName, Date createdAt) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
		this.authorName = authorName;
		this.createdAt = createdAt;
	}
//	public BlogDto(Blog updatedBlog) {
//		super();
//		this.id = updatedBlog.getId();
//		this.title = updatedBlog.getTitle();
//		this.content = updatedBlog.getContent();
//		this.authorName = updatedBlog.getAuthor().getName();
//		this.createdAt = updatedBlog.getCreatedAt();
//	}
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
	public String getAuthorName() {
		return authorName;
	}
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
    
    
}