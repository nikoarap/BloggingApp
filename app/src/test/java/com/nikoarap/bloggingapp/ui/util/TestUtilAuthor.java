package com.nikoarap.bloggingapp.ui.util;

import com.nikoarap.bloggingapp.models.Address;
import com.nikoarap.bloggingapp.ui.models.Authors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestUtilAuthor {


    private static Address address1;
    public static final Authors TEST_Authors_1 = new Authors(address1, "http://asda1.jpg",
            "Nick","1","nikoarap","tyhug@gmail.com");

    private static Address address2;
    public static final Authors TEST_Authors_2 = new Authors(address2, "http://asdawdawsdasdaasd.jpg",
            "Bill","2","nikoarap","nikoarap@gmail.com");

    public static final List<Authors> TEST_AuthorsS_LIST = Collections.unmodifiableList(
            new ArrayList<Authors>(){{
                add(new Authors(address1, "http://asda1.jpg",
                        "Nick","1","nikoarap","tyhug@gmail.com"));
                add(new Authors(address2, "http://asdawdawsdasdaasd.jpg",
                        "Bill","2","nikoarap","nikoarap@gmail.com"));
            }}
    );

}
