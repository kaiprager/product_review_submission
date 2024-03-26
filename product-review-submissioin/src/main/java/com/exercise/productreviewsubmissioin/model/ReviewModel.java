package com.exercise.productreviewsubmissioin.model;

import jakarta.validation.constraints.*;

public class ReviewModel {

    private Long id;

    @NotNull(message = "Please enter the first Name")
    @Size(min = 1, message = "Please enter the first Name")
    private String name;

    @NotNull(message = "Please enter the email.")
    @Size(min = 1, message = "Please enter the email.")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "Please enter a valid email.")
    private String email;

    @NotNull(message = "Please enter your rating (1-10, 1 = the lowest, 10 = the highest.")
    @Min(value=1, message="Must be greater than or equal to 1")
    @Max(value=10, message="Must be less than or equal to 10")
    private Integer rating;

    @NotNull(message = "Please enter your comment.")
    @Size(min = 20, message = "Please enter your comment.")
    private String comment;


    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
