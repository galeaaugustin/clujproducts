Create an API that acts as a store management tool

- Create a Github profile if you don't have one -DONE
- Use git in a verbose manner, push even if you wrote only one class -DONE
- Create a Java, maven based project, Springboot for the web part -DONE
- No front-end, you can focus on backend, no need to overcomplicate the structure -> DONE
- Implement basic functions, for example: add-product, find-product, change-price or others -> DONE
- Optional: Implement a basic authentication mechanism and role based endpoint access -> DONE
- Design error mechanism and handling plus logging -> DONE
- Write unit tests, at least for one class -> WORK IN PROGRESS
- Use Java 9+ features -> DONE 
- Add a small Readme to document the project -> DONE


- SQL: please add the following records first

create schema clujproducts;

use clujproducts;
insert into roles (id, name) value ('1','ROLE_ADMIN');
insert into roles (id, name) value ('2','ROLE_USER');