package com.tts.TechTalentBlog.repository;

import com.tts.TechTalentBlog.model.Tag;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends CrudRepository<Tag, Long> {
    Tag findByPhrase(String phrase);
}
