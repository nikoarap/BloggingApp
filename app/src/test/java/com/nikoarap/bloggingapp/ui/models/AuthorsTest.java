package com.nikoarap.bloggingapp.ui.models;

import com.nikoarap.bloggingapp.models.Address;
import com.nikoarap.bloggingapp.models.Author;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AuthorsTest {

    public Address address;

    /*
        Compare two equal Authors
     */
    @Test
    public void isAuthorsEqual_identicalProperties_returnTrue() throws Exception {

        // Arrange
        Author Author1=  new Author(address, "http://asdawdawsdasdaasd.jpg",
                "Nick","1","nikoarap","nikoarap@gmail.com");
        Author1.setId("1");
        Author Author2 =  new Author(address, "http://asdawdawsdasdaasd.jpg",
                "Nick","1","nikoarap","nikoarap@gmail.com");
        Author2.setId("1");

        // Act

        // Assert
        assertEquals(Author1, Author2);
        System.out.println("The Authors are equal!");
    }


    /*
        Compare Authors with 2 different ids
     */

    @Test
    public void isAuthorsEqual_differentIds_returnFalse() throws Exception {
        // Arrange
        Author Author1 =  new Author(address, "http://asdawdawsdasdaasd.jpg",
                "Nick","1","nikoarap","nikoarap@gmail.com");
        Author1.setId("1");
        Author Author2 =  new Author(address, "http://asdawdawsdasdaasd.jpg",
                "Nick","1","nikoarap","nikoarap@gmail.com");
        Author2.setId("2");

        // Act

        // Assert
        assertEquals(Author1, Author2);
        System.out.println("The Authors are not equal!");
    }

    /*
        Compare two Authors with different timestamps
     */

    @Test
    public void isAuthorsEqual_differentEmails_returnTrue() throws Exception {
        // Arrange
        Author Author1 =  new Author(address, "http://asdawdawsdasdaasd.jpg",
                "Nick","1","nikoarap","nikoarap@gmail.com");
        Author1.setId("1");
        Author Author2 =  new Author(address, "http://asdawdawsdasdaasd.jpg",
                "Nick","1","nikoarap","nikoara@gmail.com");
        Author2.setId("1");

        // Act

        // Assert
        assertEquals(Author1, Author2);
        System.out.println("The Authors are equal!");
    }

    /*
        Compare two Authors with different titles
     */
    @Test
    public void isAuthorsEqual_differentName_returnFalse() throws Exception {
        // Arrange
        Author Author1 =  new Author(address, "http://asdawdawsdasdaasd.jpg",
                "Nick","1","nikoarap","nikoarap@gmail.com");
        Author1.setId("1");
        Author Author2 =  new Author(address, "http://asdawdawsdasdaasd.jpg",
                "Bill","1","nikoarap","nikoarap@gmail.com");
        Author2.setId("1");

        // Act


        // Assert
        assertEquals(Author1, Author2);
        System.out.println("The Authors are not equal! They have different titles.");
    }



    /*
        Compare two Authors with different content
     */
    @Test
    public void isAuthorsEqual_differentAvatar_returnFalse() throws Exception {
        // Arrange
        Author Author1 =  new Author(address, "http://asdawdawsdasdaasd.jpg",
                "Nick","1","nikoarap","nikoarap@gmail.com");
        Author1.setId("1");
        Author Author2 =  new Author(address, "http://asdawdawsdasdaasd.jpg",
                "Nick","1","nikoarap","nikoarap@gmail.com");
        Author2.setId("1");

        // Act


        // Assert
        assertEquals(Author1, Author2);
        System.out.println("The Authors are not equal! They have different content.");
    }
}
