package com.library.springbootlibrary.responsemodels;

import com.library.springbootlibrary.entity.Book;
import lombok.Data;

@Data
public class ShelfCurrentLoansResponse {

    private int daysLeft;
    private Book book;

    public ShelfCurrentLoansResponse(Book book, int daysleft){
        this.book = book;
        this.daysLeft = daysleft;
    }
}
