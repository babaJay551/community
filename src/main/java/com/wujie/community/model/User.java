package com.wujie.community.model;

public class User {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column person.id
     *
     * @mbg.generated Wed Feb 05 15:54:42 CST 2020
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column person.name
     *
     * @mbg.generated Wed Feb 05 15:54:42 CST 2020
     */
    private String name;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column person.username
     *
     * @mbg.generated Wed Feb 05 15:54:42 CST 2020
     */
    private String username;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column person.sex
     *
     * @mbg.generated Wed Feb 05 15:54:42 CST 2020
     */
    private Integer sex;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column person.bio
     *
     * @mbg.generated Wed Feb 05 15:54:42 CST 2020
     */
    private String bio;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column person.password
     *
     * @mbg.generated Wed Feb 05 15:54:42 CST 2020
     */
    private String password;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column person.avatar_url
     *
     * @mbg.generated Wed Feb 05 15:54:42 CST 2020
     */
    private String avatarUrl;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column person.salt
     *
     * @mbg.generated Wed Feb 05 15:54:42 CST 2020
     */
    private String salt;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column person.id
     *
     * @return the value of person.id
     *
     * @mbg.generated Wed Feb 05 15:54:42 CST 2020
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column person.id
     *
     * @param id the value for person.id
     *
     * @mbg.generated Wed Feb 05 15:54:42 CST 2020
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column person.name
     *
     * @return the value of person.name
     *
     * @mbg.generated Wed Feb 05 15:54:42 CST 2020
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column person.name
     *
     * @param name the value for person.name
     *
     * @mbg.generated Wed Feb 05 15:54:42 CST 2020
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column person.username
     *
     * @return the value of person.username
     *
     * @mbg.generated Wed Feb 05 15:54:42 CST 2020
     */
    public String getUsername() {
        return username;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column person.username
     *
     * @param username the value for person.username
     *
     * @mbg.generated Wed Feb 05 15:54:42 CST 2020
     */
    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column person.sex
     *
     * @return the value of person.sex
     *
     * @mbg.generated Wed Feb 05 15:54:42 CST 2020
     */
    public Integer getSex() {
        return sex;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column person.sex
     *
     * @param sex the value for person.sex
     *
     * @mbg.generated Wed Feb 05 15:54:42 CST 2020
     */
    public void setSex(Integer sex) {
        this.sex = sex;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column person.password
     *
     * @return the value of person.password
     *
     * @mbg.generated Wed Feb 05 15:54:42 CST 2020
     */
    public String getPassword() {
        return password;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column person.password
     *
     * @param password the value for person.password
     *
     * @mbg.generated Wed Feb 05 15:54:42 CST 2020
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column person.avatar_url
     *
     * @return the value of person.avatar_url
     *
     * @mbg.generated Wed Feb 05 15:54:42 CST 2020
     */
    public String getAvatarUrl() {
        return avatarUrl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column person.avatar_url
     *
     * @param avatarUrl the value for person.avatar_url
     *
     * @mbg.generated Wed Feb 05 15:54:42 CST 2020
     */
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl == null ? null : avatarUrl.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column person.salt
     *
     * @return the value of person.salt
     *
     * @mbg.generated Wed Feb 05 15:54:42 CST 2020
     */
    public String getSalt() {
        return salt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column person.salt
     *
     * @param salt the value for person.salt
     *
     * @mbg.generated Wed Feb 05 15:54:42 CST 2020
     */
    public void setSalt(String salt) {
        this.salt = salt == null ? null : salt.trim();
    }
}