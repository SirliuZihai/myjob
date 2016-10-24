package com.zihai.entity;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable{
 
	private static final long serialVersionUID = 1L;

	private String username;

    private String password;

    private String email;

    private String phone;

    private String question;

    private String answer;

    private String auth;

    private Date makedatetime;

    private Date modifydatetime;
    
    /**
     * 关联查询 Account
     * */
    private Account account;
    /**
     * 关联查询userinfo
     * */
    private UserInfo userinfo;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question == null ? null : question.trim();
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer == null ? null : answer.trim();
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth == null ? null : auth.trim();
    }

    public Date getMakedatetime() {
        return makedatetime;
    }

    public void setMakedatetime(Date makedatetime) {
        this.makedatetime = makedatetime;
    }

    public Date getModifydatetime() {
        return modifydatetime;
    }

    public void setModifydatetime(Date modifydatetime) {
        this.modifydatetime = modifydatetime;
    }

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public UserInfo getUserinfo() {
		return userinfo;
	}

	public void setUserinfo(UserInfo userinfo) {
		this.userinfo = userinfo;
	}
    
}