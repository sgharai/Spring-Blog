package com.tts.TechTalentBlog.service;

import com.tts.TechTalentBlog.model.BlogPost;
import com.tts.TechTalentBlog.model.Tag;
import com.tts.TechTalentBlog.repository.BlogPostRepository;
import com.tts.TechTalentBlog.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BlogPostService {
    @Autowired
    private BlogPostRepository blogPostRepository;

    @Autowired
    private TagRepository tagRepository;

    private void handleTags(BlogPost blogPost) {
        List<Tag> tags = new ArrayList<>();
        String tagListString = blogPost.getTagListString();
        String[] tagListArr = tagListString.split(",");

        for (String tagString : tagListArr) {
            Tag tag = tagRepository.findByPhrase(tagString);
            if (tag == null) {
                tag = new Tag();
                tag.setPhrase(tagString);
                tagRepository.save(tag);
            }
            tags.add(tag);
        }
        blogPost.setTags(tags);
    }

    public BlogPost save(BlogPost blogPost){
        handleTags(blogPost);
        blogPostRepository.save(blogPost);
        return blogPost;
    }

    public List<BlogPost> findAllWithTag(String tag) {
        List<BlogPost> posts = blogPostRepository.findByTags_Phrase(tag);
        return posts;
    }
}
