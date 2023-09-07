package com.waseemshahzad.alpha4.model;

//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
// /*
// *  Created By Adnan Bashir Manak on 11/30/21, 12:23 PM
// *  Copyright Ⓒ 2021. All rights reserved Ⓒ 2021 http://pksofter.blogspot.com/
// *  Last modified: 11/30/21, 12:22 PM
// * Whatsapp : +923328695758
// * Email : AdnanBashirManak@gmail.com
// */
//%%%%%%%%%%%%%%%%%%%%%%%%%%%%  %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
import java.io.Serializable;

public class ContactsModel implements Serializable {
    public String name;
    public String phoneNumber;

    public ContactsModel() {
    }

    public ContactsModel(String name, String phoneNumber ) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}