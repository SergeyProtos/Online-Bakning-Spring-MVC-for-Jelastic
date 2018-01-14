<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>TopBank Online</title>
      <style>
          <%@include file="index.css"%>
      </style>
  </head>
  <body>
      <h4 class="title" align="center">Welcome in TopBank Online Banking</h4>
      <h4 class="message" align="center">${message}</h4>
      <div align="center">
            <form action="/login" method="POST">
                <fieldset>
                    <p align="center"><b>If you're registered user sign in below</b></p>
                    Login: <br>
                    <input type="text" name="login" value="user"><br>
                    Password: <br>
                    <input type="text" name="password" value="user"><br>
                    <input type="submit" value="Sign in"/>
                </fieldset>
            </form>
        </div>

      <div align="center">
            <form action="/sign_up" method="POST">
                <fieldset>
                    <p align="center"><b>If you are new user please sign up below</b></p>
                    Login*: <br>
                    <input type="text" name="login" value=""><br>
                    Password*: <br>
                    <input type="text" name="password" value=""><br>
                    Repeat password*: <br>
                    <input type="text" name="passwordRepeat" value=""><br>
                    First Name*: <br>
                    <input type="text" name="firstName" value=""><br>
                    Last Name*: <br>
                    <input type="text" name="lastName" value=""><br>
                    E-mail: <br>
                    <input type="text" name="email" value=""><br>
                    Phone: <br>
                    <input type="text" name="phone" value=""><br>
                    *Mandatory data<br>
                <input type="submit" value="Sign up"/>
                </fieldset>
            </form>
        </div>
    </body>
</html>
