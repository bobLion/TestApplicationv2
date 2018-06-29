package com.example.bob.testlistener.database.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Property;

/**
 * @package com.example.bob.testlistener.database
 * @fileName User
 * @Author Bob on 2018/4/24 11:12.
 * @Describe TODO
 */

@Entity
public class User {
    @Id (autoincrement = true)
    private Long id;
    @Property(nameInDb = "user_name")
    private String userName;
    @Property(nameInDb = "user_pswd")
    private String password;
    @Property(nameInDb = "gender")
    private Integer gender;
    public User() {
    }
    public User(Long id, String userName, String password) {
        this.id = id;
        this.userName = userName;
        this.password = password;
    }
    @Generated(hash = 403572647)
    public User(Long id, String userName, String password, Integer gender) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.gender = gender;
    }
    public Long getId() {
        return this.id;
    }

    public String getUserName() {
        return this.userName;
    }
    public User setUserName(String userName) {
        this.userName = userName;
        return this;
    }
    public String getPassword() {
        return this.password;
    }
    public User setPassword(String password) {
        this.password = password;
        return this;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Integer getGender() {
        return this.gender;
    }
    public void setGender(Integer gender) {
        this.gender = gender;
    }
}
