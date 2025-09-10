package com.Coorg.BlogPost.controller;


//import com.Coorg.BlogPost.service.AuthService;
//import com.Coorg.BlogPost.service.BlogService;
//import com.Coorg.BlogPost.model.User;
//import com.Coorg.BlogPost.model.Blog;
//import com.Coorg.BlogPost.dto.BlogDto;

import jakarta.servlet.http.HttpSession;

//import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Coorg.BlogPost.dto.BlogDto;
//import com.Coorg.BlogPost.dto.BlogDto;
import com.Coorg.BlogPost.model.Blog;
import com.Coorg.BlogPost.model.User;
import com.Coorg.BlogPost.service.AuthService;
import com.Coorg.BlogPost.service.BlogService;
//import com.Coorg.BlogPost.dto.BlogDto;

//import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/blogs")
@CrossOrigin
public class BlogController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private AuthService authService;

    @GetMapping
    public  Page<BlogDto> getAllBlogs(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        
//    	Blog data = (Blog) blogService.getAllBlogs(page, size);
    	return blogService.getAllBlogs(page, size) ;
    }

    @GetMapping("/{id}/{email}")
    public ResponseEntity<?> getBlogById(@PathVariable Long id,@PathVariable String email, HttpSession session) {
    	
    	User user = authService.getUser(email).get();
    	 Long currentUserId = user.getId();
//        Long currentUserId = (Long) session.getAttribute("userId");

        return blogService.getBlogById(id)
                .map(blog -> {
                	System.out.println(blog);
                    Map<String, Object> response = new HashMap<>();
                    response.put("id", blog.getId());
                    response.put("title", blog.getTitle());
                    response.put("content", blog.getContent());
                    response.put("createdAt", blog.getCreatedAt());
                    response.put("author", Map.of("name", blog.getAuthor().getName(),"UserId",blog.getAuthor().getId()));
          

                    boolean isAuthor = (currentUserId != null) && (currentUserId.equals(blog.getAuthor().getId()));
                    response.put("isAuthor", isAuthor);
//                    System.out.println(response);
                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createBlog(@RequestBody Blog blog, HttpSession session) {
    	User user = blog.getAuthor();
    	Long userId = user.getId();
//    	System.out.println(Id);
//        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(401).body(Collections.singletonMap("error", "Unauthorized. You must be logged in."));
        }
        try {
            Blog createdBlog = blogService.createBlog(blog, userId);
//            System.out.println(createdBlog);
            return ResponseEntity.ok(createdBlog);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", e.getMessage()));
        }
    }

    @PutMapping("/{BlogId}/{userId}")
    public ResponseEntity<?> updateBlog(@PathVariable Long BlogId,@PathVariable Long userId, @RequestBody Blog blog, HttpSession session) {
        if (userId == null) {
            return ResponseEntity.status(401).body(Collections.singletonMap("error", "Unauthorized."));
        }
        try {
            BlogDto updatedBlog = blogService.updateBlog(BlogId, blog, userId);
//            BlogDto updated = new BlogDto(updatedBlog);
            
//            System.out.println(updated);
//            return null;
            return ResponseEntity.ok(updatedBlog);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}/{userId}")
    public ResponseEntity<?> deleteBlog(@PathVariable Long id,@PathVariable Long userId, HttpSession session) {
    	System.out.println(id);
    	System.out.println(userId);
//        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(401).body(Collections.singletonMap("error", "Unauthorized."));
        }
        try {
            blogService.deleteBlog(id, userId);
            return ResponseEntity.ok(Collections.singletonMap("message", "Blog deleted successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", e.getMessage()));
        }
    }
}
