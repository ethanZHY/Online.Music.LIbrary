package com.cs5200.web.jdbc;

public class Comments {
	
	private String content;
	private int commentsTo;
	private int commentedBy;
	
	public Comments(String content, int comments, int commentedBy) {
		this.content = content;
		this.commentsTo = comments;
		this.commentedBy = commentedBy;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getComments() {
		return commentsTo;
	}

	public void setComments(int comments) {
		this.commentsTo = comments;
	}

	public int getCommentedBy() {
		return commentedBy;
	}

	public void setCommentedBy(int commentedBy) {
		this.commentedBy = commentedBy;
	}

}
