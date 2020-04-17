package com.tts.TechTalentBlog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BlogPost {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column( name = "post_id")
	private Long id;
	private String title;
	private String author;
	private String blogEntry;
	private String tagListString;



	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "post_tag", joinColumns = @JoinColumn(name = "post_id"),
			inverseJoinColumns = @JoinColumn(name = "tag_id"))
	private List<Tag> tags;

}
