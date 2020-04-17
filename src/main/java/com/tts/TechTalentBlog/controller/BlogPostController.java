package com.tts.TechTalentBlog.controller;

import java.util.ArrayList;
import java.util.List;

import com.tts.TechTalentBlog.repository.BlogPostRepository;
import com.tts.TechTalentBlog.model.BlogPost;
import com.tts.TechTalentBlog.service.BlogPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class BlogPostController {
	private static List<BlogPost> posts = new ArrayList<>();
	
	@Autowired
	private BlogPostRepository blogPostRepository;

	@Autowired
	private BlogPostService blogPostService;
	
	@GetMapping("/")
	public String index(Model model) {

		// What I used to fix issue: This is returning from our h2 database
		
		List<BlogPost> postsToDisplay = blogPostRepository.findAll(); 
		model.addAttribute("posts", postsToDisplay);
		
		//
		
		// What we had: This is returning from a static variable
		// model.addAttribute("posts", posts);
		//
		
		return "blogpost/index";
	}
	
	@GetMapping("/posts/new")
	public String newPost (BlogPost blogPost) {
		return "blogpost/new";
	}
	
	@PostMapping("/posts")
	public String addPost(BlogPost blogPost, Model model) {
//		BlogPost savedPost = blogPostRepository.save(blogPost);
		BlogPost savedPost = blogPostService.save(blogPost);
		posts.add(savedPost);
		model.addAttribute("post", savedPost);
		return "blogpost/result";
	}
	
	@GetMapping("/posts/update/{id}")
	public String updateForm(BlogPost blogPost, @PathVariable Long id, Model model) {
		model.addAttribute("postIdToChange", id);
		return "blogpost/update";
	}
	
	@PostMapping("/posts/update/{id}")
	public String updatePost(BlogPost blogPost, Model model, @PathVariable Long id) {

		BlogPost blogPostById = blogPostRepository.findBlogPostById(id);
		
		blogPostById.setTitle(blogPost.getTitle());
		blogPostById.setAuthor(blogPost.getAuthor());
		blogPostById.setBlogEntry(blogPost.getBlogEntry());
		
		blogPostRepository.save(blogPostById);
		// I also made an adjustment. Instead of loading the file I wanted to redirect instread
		return "redirect:/";
		//
	}

	@RequestMapping(value = "/posts/{id}", method = RequestMethod.DELETE)
	public String deletePostWithId(@PathVariable Long id,
								   BlogPost blogPost) {

		blogPostRepository.deleteById(id);
		return "redirect:/";

	}

	@GetMapping(value = "/posts/{tag}")
	public String getPostsByTag(@PathVariable(value="tag") String tag, Model model) {
		List<BlogPost> postList = blogPostService.findAllWithTag(tag);
		model.addAttribute("postList", postList);
		model.addAttribute("tag", tag);
		return "blogpost/taggedPosts";
	}
	
}
