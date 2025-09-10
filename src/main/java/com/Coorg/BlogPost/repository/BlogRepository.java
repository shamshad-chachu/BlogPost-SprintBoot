package com.Coorg.BlogPost.repository;


import com.Coorg.BlogPost.model.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Blog, Long> {
}
