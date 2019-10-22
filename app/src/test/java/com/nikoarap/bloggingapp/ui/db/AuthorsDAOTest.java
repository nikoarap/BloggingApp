package com.nikoarap.bloggingapp.ui.db;

import android.database.sqlite.SQLiteConstraintException;

import com.nikoarap.bloggingapp.models.Address;
import com.nikoarap.bloggingapp.ui.models.Authors;
import com.nikoarap.bloggingapp.ui.util.LiveDataUtilTest;
import com.nikoarap.bloggingapp.ui.util.TestUtilAuthor;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AuthorsDAOTest extends AppDatabaseTest {

    private static  Address address;

    private static final Address TEST_ADDRESS = address;
    private static final String TEST_AVATAR = "http://3453453453.jpg";
    private static final String TEST_NAME = "Nick";
    private static final String TEST_ID = "6";
    private static final String TEST_EMAIL = "blabla@gmail.com";

         /*
            Insert, Read, Delete
         */
    @Test
    public void insertReadDelete() throws Exception{

        Authors Authors = new Authors(TestUtilAuthor.TEST_Authors_1);

        // insert
        getAuthorDao().insertAuthors(Authors); // wait until inserted

        // read
        LiveDataUtilTest liveDataTestUtil = new LiveDataUtilTest();
        List<Authors> insertedAuthors = (List<com.nikoarap.bloggingapp.ui.models.Authors>) liveDataTestUtil.getValue(getAuthorDao().getAuthors());

        assertNotNull(insertedAuthors);

        assertEquals(Authors.getAddress(), insertedAuthors.get(0).getAddress());
        assertEquals(Authors.getAvatarUrl(), insertedAuthors.get(0).getAvatarUrl());
        assertEquals(Authors.getEmail(), insertedAuthors.get(0).getEmail());
        assertEquals(Authors.getId(), insertedAuthors.get(0).getId());
        assertEquals(Authors.getName(), insertedAuthors.get(0).getName());
        assertEquals(Authors.getUserName(), insertedAuthors.get(0).getUserName());

        Authors.setId(insertedAuthors.get(0).getId());
        assertEquals(Authors, insertedAuthors.get(0));


        // confirm the database is empty
        insertedAuthors = (List<com.nikoarap.bloggingapp.ui.models.Authors>) liveDataTestUtil.getValue(getAuthorDao().getAuthors());
        assertEquals(0, insertedAuthors.size());
    }


    /*
        Insert, Read, Update, Read, Delete,
     */
    @Test
    public void insertReadUpdateReadDelete() throws Exception{

        Authors Authors = new Authors(TestUtilAuthor.TEST_Authors_1);

        // insert
        getAuthorDao().insertAuthors(Authors); // wait until inserted

        // read
        LiveDataUtilTest liveDataTestUtil = new LiveDataUtilTest();
        List<Authors> insertedAuthors = (List<com.nikoarap.bloggingapp.ui.models.Authors>) liveDataTestUtil.getValue(getAuthorDao().getAuthors());

        assertNotNull(insertedAuthors);

        assertEquals(Authors.getAddress(), insertedAuthors.get(0).getAddress());
        assertEquals(Authors.getAvatarUrl(), insertedAuthors.get(0).getAvatarUrl());
        assertEquals(Authors.getEmail(), insertedAuthors.get(0).getEmail());
        assertEquals(Authors.getId(), insertedAuthors.get(0).getId());
        assertEquals(Authors.getName(), insertedAuthors.get(0).getName());
        assertEquals(Authors.getUserName(), insertedAuthors.get(0).getUserName());

        Authors.setId(insertedAuthors.get(0).getId());
        assertEquals(Authors, insertedAuthors.get(0));

        // read
        insertedAuthors = (List<com.nikoarap.bloggingapp.ui.models.Authors>) liveDataTestUtil.getValue(getAuthorDao().getAuthors());


        assertEquals(TEST_NAME, insertedAuthors.get(0).getName());
        assertEquals(TEST_ID, insertedAuthors.get(0).getId());
        assertEquals(TEST_EMAIL, insertedAuthors.get(0).getEmail());
        assertEquals(TEST_AVATAR, insertedAuthors.get(0).getAvatarUrl());
        assertEquals(TEST_ADDRESS, insertedAuthors.get(0).getAddress());

        Authors.setId(insertedAuthors.get(0).getId());
        assertEquals(Authors, insertedAuthors.get(0));


        // confirm the database is empty
        insertedAuthors = (List<com.nikoarap.bloggingapp.ui.models.Authors>) liveDataTestUtil.getValue(getAuthorDao().getAuthors());
        assertEquals(0, insertedAuthors.size());
    }



    /*
        Insert Authorss with null title, throw exception
     */
    @Test(expected = SQLiteConstraintException.class)
    public void insert_nullTitle_throwSQLiteConstraintException() throws Exception{

        final Authors Authors = new Authors(TestUtilAuthor.TEST_Authors_1);
        Authors.setName(null);

        // insert
        getAuthorDao().insertAuthors(Authors);
    }


    /*
        Insert, Update with null title, throw exception
     */

    @Test(expected = SQLiteConstraintException.class)
    public void updateAuthors_nullTitle_throwSQLiteConstraintException() throws Exception{

        Authors Authors = new Authors(TestUtilAuthor.TEST_Authors_1);

        // insert
        getAuthorDao().insertAuthors(Authors);

        // read
        LiveDataUtilTest liveDataTestUtil = new LiveDataUtilTest();
        List<Authors> insertedAuthors = (List<com.nikoarap.bloggingapp.ui.models.Authors>) liveDataTestUtil.getValue(getAuthorDao().getAuthors());
        assertNotNull(insertedAuthors);



    }

}
