package com.project.shopapp.responses;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.shopapp.models.Category;
import com.project.shopapp.models.Comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentResponse {
	@JsonProperty("content")
	private String content;
	
	@JsonProperty("user")
	private UserResponse userResponse;
	
	@JsonProperty("updated_at")
	private LocalDateTime updatedAt;
	
	public static CommentResponse fromComment(Comment comment) {
		return CommentResponse.builder()
				.content(comment.getContent())
				.userResponse(UserResponse.fromUser(comment.getUser()))
				.updatedAt(comment.getCreatedAt())
				.build();
	}
}
