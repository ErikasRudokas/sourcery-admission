package com.erik.Sourcery.mappers.impl;

import com.erik.Sourcery.domain.dtos.ReviewDto;
import com.erik.Sourcery.domain.entities.Review;
import com.erik.Sourcery.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper implements Mapper<Review, ReviewDto> {

    private final ModelMapper modelMapper;

    public ReviewMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    @Override
    public ReviewDto mapTo(Review review) {
        return modelMapper.map(review, ReviewDto.class);
    }

    @Override
    public Review mapFrom(ReviewDto reviewDto) {
        return modelMapper.map(reviewDto, Review.class);
    }
}
