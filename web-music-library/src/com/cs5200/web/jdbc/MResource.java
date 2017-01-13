package com.cs5200.web.jdbc;


public class MResource {
	
	private int id;
	private String title;
	private String artist;
	private String album;
	private Genre genre;
//	private String lyric;
	private double length;
	private int releaseYear;
	

	public MResource(int id, String title) {
		this.id = id;
		this.title = title;
	}

	public MResource(String title, String artist, String album, 
			Genre genre, double length, int releaseYear) {
		this.title = title;
		this.artist = artist;
		this.album = album;
		this.genre = genre;
		this.length = length;
		this.releaseYear = releaseYear;
	}

	public MResource(int id, String title, String artist, String album, 
			Genre genre, double length, int releaseYear) {
		this.id = id;
		this.title = title;
		this.artist = artist;
		this.album = album;
		this.genre = genre;
		this.length = length;
		this.releaseYear = releaseYear;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public Genre getGenre() {
		return genre;
	}

	public void setGenre(Genre genre) {
		this.genre = genre;
	}

	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}

	public int getReleaseYear() {
		return releaseYear;
	}

	public void setReleaseYear(int releaseYear) {
		this.releaseYear = releaseYear;
	}

}
