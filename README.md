# Spring Security + Jwt Authentication + Java Mail Service with HTML Templates
This project demonstrates the implementation of JSON Web Token (JWT) based authentication using the latest Spring Security, along with integration of JavaMail for sending emails.

## Key features
- Comprehensive illustration of JWT authentication implementation using Spring Security version 6+
- Utilizes H2 in-memory database for easy and efficient demonstration purposes
- Incorporates Jakarta Mail for seamless email integration
- Provides endpoints for essential functionalities like login, registration, confirmation and role testing
- Utilizes lombok to reduce boilerplate code and make it cleaner and easier to read
- Utilizes JPA for swift and straightforward database operations configuration

## Setting up
1. To be able to send emails, you need to configure a few things in this project. Below is a brief guide to create an application password from your Gmail account, which will enable you to send emails from that account using SMTP.

2. If you are using two-factor authentication, then entering your regular Gmail password into the properties is not going to work. In this case, you’re going to need an app-specific password.

3. To get one, go to the [App Passwords](https://myaccount.google.com/apppasswords) section of the Google account manager, drop down the menu, select `Other`, set the name of the password and click on `Generate`. This will display a secret password to you, remember to copy it because when you close the popup, you wont be able to see the password ever again.

4. Now navigate to `src/main/resources/application.properties` and modify the following properties:
```properties
spring.mail.username= your_mail_here@gmail.com
spring.mail.password= your_app_password_here
```
Now you're ready to test the registration with email confirmation!

## Licensing
This project is distributed under the GNU General Public License v3 (GPL-3.0). 
This license guarantees your freedom to use, modify, and distribute the software.
