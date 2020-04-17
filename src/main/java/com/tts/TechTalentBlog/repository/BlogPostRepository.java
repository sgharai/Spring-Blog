package com.tts.TechTalentBlog.repository;

import com.tts.TechTalentBlog.model.BlogPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlogPostRepository extends JpaRepository<BlogPost, Long>{
	BlogPost findBlogPostById(Long id);
	List<BlogPost> findByTags_Phrase(String tagPhrase);
}
