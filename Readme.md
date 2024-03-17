Create an API that acts as a store management tool

Create a Github profile if you don't have one -done
Use git in a verbose manner, push even if you wrote only one class -done
Create a Java, maven based project, Springboot for the web part -done
No front-end, you can focus on backend, no need to overcomplicate the structure
Implement basic functions, for example: add-product, find-product, change-price or others
Optional: Implement a basic authentication mechanism and role based endpoint access
Design error mechanism and handling plus logging
Write unit tests, at least for one class
Use Java 9+ features
Add a small Readme to document the project


SQL: please add the following records first

create schema clujproducts;

use clujproducts;
insert into roles (id, name) value ('1','ROLE_ADMIN');
insert into roles (id, name) value ('2','ROLE_USER');