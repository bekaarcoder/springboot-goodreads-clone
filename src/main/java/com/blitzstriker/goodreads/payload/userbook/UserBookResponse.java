package com.blitzstriker.goodreads.payload.userbook;

import com.blitzstriker.goodreads.payload.book.BookResponse;
import com.blitzstriker.goodreads.payload.user.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserBookResponse {
    private Date startDate;
    private Date endDate;
    private String status;
    private BookResponse book;
}
