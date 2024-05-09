package com.project.shopapp.services.comment;

import java.util.List;

import com.project.shopapp.dtos.CommentDTO;
import com.project.shopapp.exceptions.DataNotFoundException;
import com.project.shopapp.models.Comment;
import com.project.shopapp.responses.CommentResponse;

public interface ICommentService {
	Comment insertComment(CommentDTO comment);
	
	void deleteComment(Long commentId);
	
	void updateComment(Long id, CommentDTO comment) throws DataNotFoundException;
	
	List<CommentResponse> getCommentsByUserAndProduct(Long userId, Long productId);
	
	List<CommentResponse> getCommentsByProduct(Long productId);
	
}