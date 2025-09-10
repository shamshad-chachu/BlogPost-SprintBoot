package com.Coorg.BlogPost.service;


//import com.Coorg.BlogPost.model.Blog;
//import com.Coorg.BlogPost.model.User;
//import com.Coorg.BlogPost.repository.BlogRepository;
//import com.Coorg.BlogPost.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.Coorg.BlogPost.dto.BlogDto;
import com.Coorg.BlogPost.model.Blog;
import com.Coorg.BlogPost.model.User;
import com.Coorg.BlogPost.repository.BlogRepository;
import com.Coorg.BlogPost.repository.UserRepository;

//import com.Coorg.BlogPost.dto.BlogDto;
import java.util.Optional;

@Service
public class BlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private UserRepository userRepository;

    public Page<Blog> getAllBlogs(int page, int size) {
//        return blogRepository.findAll(PageRequest.of(page, size));
    	
    	
    	 Page<Blog> blogs = blogRepository.findAll(PageRequest.of(page, size));
    	    
    	    // Map the Page of Blog entities to a Page of BlogDto objects
    	    return blogs.map(blog -> {
    	        Blog dto = new Blog();
    	        dto.setId(blog.getId());
    	        dto.setTitle(blog.getTitle());
    	        dto.setContent(blog.getContent());
    	        dto.setCreatedAt(blog.getCreatedAt());
    	        dto.setAuthor(blog.getAuthor()); // This will now trigger the lazy load
    	        return dto;
    	    });
    	
    }

    public Optional<Blog> getBlogById(Long id) {
        return blogRepository.findById(id);
    }
    
    	//create blog
    public Blog createBlog(Blog blog, Long userId) {
//    	User author = userRepository.findById(userId)
//              .orElseThrow(() -> new RuntimeException("User not found"));
//    	System.out.println(blog);
//    	System.out.println(author);
    	
//    	return null;
    	
//        User author = userRepository.findById(userId)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//        blog.setAuthor(author);
    	
        return blogRepository.save(blog);
    }
    
    		//update blog
    public Blog updateBlog(Long id, Blog blogUpdates, Long currentUserId) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Blog not found"));
        if (!(blog.getAuthor().getId() == currentUserId)){
        	System.out.println("in if");
            throw new RuntimeException("Unauthorized: You are not the author of this blog.");
        }
        Optional<User> author = userRepository.findById(currentUserId);
        blog.setTitle(blogUpdates.getTitle());
        blog.setContent(blogUpdates.getContent());
        Blog blogdto = blogRepository.save(blog);
      
        return blogdto;
    }
    
    	// delete blog
    public void deleteBlog(Long id, Long currentUserId) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Blog not found"));
        if (!blog.getAuthor().getId().equals(currentUserId)) {
            throw new RuntimeException("Unauthorized: You are not the author of this blog.");
        }
        blogRepository.delete(blog);
    }
}
