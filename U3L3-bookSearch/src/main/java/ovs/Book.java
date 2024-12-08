/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ovs;

/**
 * File Name: Book.java
 * Person Programmer: Steven Passynkov 
 * Date: 14 Sep 2024 
 * Description: Book bean
 */
class Book {
// id of book
    private int id;
    // title of book
    private String title;

    /**
     * Book constructor
     * @param id id
     * @param title title
     */
    public Book(int id, String title) {
        // assign id
        this.id = id;
        // assing title
        this.title = title;
    }

    /**
     * Get id of book
     * @return id of book
     */
    public int getId() {
        return id;
    }

    /**
     * Get title of book
     * @return title of book
     */
    public String getTitle() {
        return title;
    }
}
