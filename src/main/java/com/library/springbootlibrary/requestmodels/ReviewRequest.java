package com.library.springbootlibrary.requestmodels;

import lombok.Data;

import java.util.Optional;

@Data
public class ReviewRequest {

    public double rating;

    public long bookId;

    private Optional<String> reviewDescription;
}
