package com.example.javafxendassignement2022.controller;

import com.example.javafxendassignement2022.database.ItemDataBase;
import com.example.javafxendassignement2022.database.MemberDatabase;

public class BaseController {
    protected ItemDataBase itemDataBase;
    protected MemberDatabase memberDatabase;

    public BaseController() {
        itemDataBase = new ItemDataBase();
        memberDatabase = new MemberDatabase();
    }


}
