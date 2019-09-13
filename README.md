# Lab: Build an app using a cloud based NoSQL Database

## Overview
Today, you'll start building a new application called TaskMaster. It's a task-tracking application with the same basic goal as Trello: allow users to keep track of tasks to be done and their status. While we'll start today with a basic feature set, we will continue building out the capabilities of this application over time.

The reason we're starting small on this server is because the main focus for the rest of the course is not full-stack web development, but is instead to gain experience with different features of Cloud Development. Everything we build will have the added task of deployment using the cloud. Today, you'll get to use a remote NoSQL database as the data store for your application. As we continue to build out our skill with NoSQL, that structure of our data will be our main focus for future development on TaskMaster.

## Feature Tasks
- A user should be able to make a GET request to /tasks and receive JSON data representing all of the tasks.
- Each task should have a title, description, and status.
- A user should be able to make a POST request to /tasks with body parameters for title and description to add a new task.
- All tasks should start with a status of Available.
- The response to that request should contain the complete saved data for that task.
- A user should be able to make a PUT request to /tasks/{id}/state to advance the status of that task.
- Tasks should advance from Available -> Assigned -> Accepted -> Finished.
- A user should be able to access this application on the Internet.
- The application should be deployed to EC2, with the database on DynamoDB.
- You should also use DynamoDB for your local application testing; in other words, you should connect to your production database, even in your development environment. (This is generally a bad practice, and we'll see how to work differently soon.)

## Deployed Site
- [Taskmaster](http://taskmaster-dev-1.us-west-2.elasticbeanstalk.com/api/v1/tasks)

## Resources
- Nic Paro (Super helpful!!!)
- Melfi Perez
- Brandon Hurrington
- Chris Coulon
- Travis Cox

