# quote-app
This application for quote app with authentication mechanism.
#
To switch to the front part with Thymeleaf, mark this block of code in the security configuration.
https://github.com/yermukhanJJ/quote-app/blob/master/src/main/java/com/kameleoon/trial/task/quote/security/WebSecurityConfig.java#:~:text=*/-,//%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20.exceptionHandling().authenticationEntryPoint(unauthorizedHandler),//%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20.and(),-/**
# ![image](https://user-images.githubusercontent.com/98425087/218262445-ddfbabcb-3a70-4750-85da-f75f7944cafd.png)
# Thymeleaf front part
at address: http://localhost:8181/ you will be taken to the login page. You have create account by the link.
# RestFul application
Now comment out the above part of the code. You have send rest requests. 

# for registration to system POST http://localhost:8181/app/sign-up
Example request body:

{

    "username":"yermukhan",
    "email":"yermukhan@gmail.com",
    "password":"yermukhan"
    
}

# for authorization POST http://localhost:8181/app/sign-in
Example request body: 
{

    "username":"yermukhan",
    "password":"yermukhan"

}

Response:
![image](https://user-images.githubusercontent.com/98425087/218263044-be75df6d-2c4c-462d-8f35-b88b707350b6.png)

copy "token" and paste to bearer token
![image](https://user-images.githubusercontent.com/98425087/218263099-1d86c3e1-bfb2-4a67-9706-b42a0aeb6b62.png)

# Viewing quote by id GET: http://localhost:8181/quote/{id} 

# Viewing user-created quotes GET: http://localhost:8181/quote/myquotes

# View of the top 10 quotes GET: http://localhost:8181/quote/top10

# View of the worse 10 quotes GET: http://localhost:8181/quote/flop10

# Get a random quote GET: http://localhost:8181/quote/random

# Put a like(or upvote) GET: http://localhost:8181/quote/like/{id}
id = quote id

# Put a dislike(or downvote) GET: http://localhost:8181/quote/dislike/{id}
id = quote id

# View user profile GET: http://localhost:8181/quote/profile/{username}

# Create new quote POST: http://localhost:8181/quote/create
Request body: 

{
    
    "title":"Quote1",
    
    "content":"Quote1 content"
    
}

# Modify the quote PUT: http://localhost:8181/quote/edit/{id}
id = id quote
Request body: 

{

    "title":"Quote1",
    "content":"Quote1 content edit"
    
}

# Delete quote DELETE: http://localhost:8181/quote/remove/{id}
id = id quote

