package com.exercise.productreviewsubmissioin.controller;

import com.exercise.productreviewsubmissioin.entity.Review;
import com.exercise.productreviewsubmissioin.model.ReviewModel;
import com.exercise.productreviewsubmissioin.repo.ReviewRepo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/review")
public class ReviewController {


    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @Autowired
    private ReviewRepo reviewRepo;


    @RequestMapping("/all-reviews")
    public String displayReviews(Model model) {

        List<Review> reviews = reviewRepo.findAll();
        model.addAttribute("reviews", reviews);

        return "display-reviews";
    }

    @RequestMapping("/create")
    public String showAddForm(Model model) {

        model.addAttribute("reviewModel", new ReviewModel());

        return "add-review-form";
    }

    @PostMapping("/create")
    public String processAddForm(@Valid @ModelAttribute("reviewModel") ReviewModel reviewModel, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            return "add-review-form";
        } else {
            Review review = mapModelToEntity(reviewModel);
            reviewRepo.save(review);
            return "redirect:/review/all-reviews";
        }
    }

    @RequestMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        Optional<Review> optionalReview = reviewRepo.findById(id);
        if (optionalReview.isPresent()) {
            ReviewModel reviewModel = mapEntityToModel(optionalReview.get());
            model.addAttribute("reviewModel", reviewModel);
            return "update-review-form";
        } else {
            return "redirect:/review/all-reviews";
        }
    }

    @PostMapping("/edit/{id}")
    public String processUpdateForm(@PathVariable Long id, @Valid @ModelAttribute("reviewModel") ReviewModel reviewModel, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "update-review-form";
        } else {
            Optional<Review> optionalReview = reviewRepo.findById(id);
            if (optionalReview.isPresent()) {
                Review existingReview = optionalReview.get();

                Review updatedReview = mapModelToEntity(reviewModel);

                updateReview(existingReview, updatedReview);

                reviewRepo.save(existingReview);
            }
            return "redirect:/review/all-reviews";
        }
    }


    @RequestMapping("/delete/{id}")
    public String deleteReview(@PathVariable Long id) {
        reviewRepo.deleteById(id);
        return "redirect:/review/all-reviews";
    }




    private Review mapModelToEntity(ReviewModel reviewModel) {
        Review review = new Review();
        review.setName(reviewModel.getName());
        review.setEmail(reviewModel.getEmail());
        review.setRating(reviewModel.getRating());
        review.setComment(reviewModel.getComment());
        return review;
    }

    private ReviewModel mapEntityToModel(Review review) {
        ReviewModel reviewModel = new ReviewModel();
        reviewModel.setId(review.getId());
        reviewModel.setName(review.getName());
        reviewModel.setEmail(review.getEmail());
        reviewModel.setRating(review.getRating());
        reviewModel.setComment(review.getComment());
        return reviewModel;
    }

    private void updateReview(Review existingReview, Review updatedReview) {
        existingReview.setName(updatedReview.getName());
        existingReview.setEmail(updatedReview.getEmail());
        existingReview.setRating(updatedReview.getRating());
        existingReview.setComment(updatedReview.getComment());
    }
}
