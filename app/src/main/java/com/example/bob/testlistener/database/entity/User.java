package com.example.bob.testlistener.database.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;


/**
 * @package com.example.bob.testlistener.database.entity
 * @fileName User
 * @Author Bob on 2018/4/24 10:02.
 * @Describe TODO
 */

@Entity
public class User {
    private int id;

    private String name;

    private String password;

    @Generated(hash = 258842593)
    public User(int id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    @Generated(hash = 586692638)
    public User() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
