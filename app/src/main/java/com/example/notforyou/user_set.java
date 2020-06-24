package com.example.notforyou;

public class user_set
{
    long id ;

    private String name;
    private  String pass;

    user_set(){}
    user_set(String name,String pass)
    {
        this.name=name;
        this.pass=pass;
    }

    user_set(long ID,String name,String pass)
    {
        this.name=name;
        this.pass=pass;
        this.id=ID;
    }

    public long getID() {
        return id;
    }

    public void setID(long ID) {
        this.id = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

}
