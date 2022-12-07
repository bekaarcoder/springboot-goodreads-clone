package com.blitzstriker.goodreads.payload.userbook;

import com.blitzstriker.goodreads.entity.ReadingStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserBookDto {
    private Date startDate;
    private Date endDate;
    private ReadingStatus status;
}
