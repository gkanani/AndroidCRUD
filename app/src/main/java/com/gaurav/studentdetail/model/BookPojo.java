package com.gaurav.studentdetail.model;

import com.google.gson.annotations.SerializedName;

public class BookPojo {

    @SerializedName("authorName")
    private String authorName;
    @SerializedName("bid")
    private Integer bid;
    @SerializedName("bookName")
    private String bookName;

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Integer getBid() {
        return bid;
    }

    public void setBid(Integer bid) {
        this.bid = bid;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

}
