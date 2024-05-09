package com.project.shopapp.controllers;

import java.util.List;
import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.shopapp.dtos.CommentDTO;
import com.project.shopapp.models.Comment;
import com.project.shopapp.models.User;
import com.project.shopapp.responses.CommentResponse;
import com.project.shopapp.services.comment.CommentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("${api.prefix}/comments")
@RequiredArgsConstructor
public class CommentController {
	private final CommentService commentService;

	@GetMapping("")
	public ResponseEntity<List<CommentResponse>> getAllComments(
			@RequestParam( value = "user_id", required = false) Long userId,
			@RequestParam("product_id") long productId) {
		List<CommentResponse> commentResponses;
		if(userId == null) {
			commentResponses = commentService.getCommentsByProduct(productId);
		}else {
			commentResponses = commentService.getCommentsByUserAndProduct(userId, productId);
		}
		return ResponseEntity.ok(commentResponses);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_USER') or hasROLE('ROLE_ADMIN')")
	public ResponseEntity<?> updateCcomment(
			@PathVariable("id") Long commentId, 
			@Valid @RequestBody CommentDTO commentDTO,
			Authentication authentication) {
		try {
			User loginUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if(!Objects.equals(loginUser.getId(), commentDTO.getUserId())) {
				return ResponseEntity.badRequest().body("You cannot update another user's comment");
			}
			commentService.updateComment(commentId, commentDTO);
			return ResponseEntity.ok("Update comment successfully");
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred during comment update.");
		}
	}

	@PostMapping("")
	@PreAuthorize("hasRole('ROLE_USER') or hasROLE('ROLE_ADMIN')")
	public ResponseEntity<?> insertComment(
			@Valid @RequestBody CommentDTO commentDTO,
			Authentication authentication)
	{
		try {
			User loginUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if(loginUser.getId() != commentDTO.getUserId()) {
				return ResponseEntity.badRequest().body("You cannot comment as another user");
			}
			commentService.insertComment(commentDTO);
			return ResponseEntity.ok("Insert comment successfully");
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred during comment insertion.");
		}
	}
}
