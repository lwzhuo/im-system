package com.zhuo.imsystem.http.model;

public class JwtSubject {
    private String uid;
    private String username;
    private String instanceId;

    public JwtSubject(String uid){
        this.uid = uid;
    }

    public String toString(){
        return "{"+
                "uid:"+"\""+ uid +"\""+
                "}";
    }

    public static void main(String[] args) {
        System.out.println(new JwtSubject("aaaaa").toString());
    }
}
