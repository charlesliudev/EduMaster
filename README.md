# My Personal Project

## EduMaster

**What does the application do?**

EduMaster if a financial management software for school administrators to better keep record of monetary transactions
with students and teachers. It will allow the often manual, time-consuming task of managing tuition fees and salaries to
be done in more efficiently. School administrators will have a better understanding of their cash flows that are needed 
to make important management decisions.

The user can register new students or teachers into the database on demand, as well as see what courses each student is
enrolled in and what courses each teacher teaches. The tuition fees due from each student and the salaries due to
teachers are periodically accrued such that users can view at any time the status of all cash inflows and outflows. 
When a student pays his/her fees, or a teacher receives a salary, the transaction can be recorded in the application
against the outstanding amounts. Users also have the option to view the school's entire transaction history with a 
particular student or teacher.

**Why?**

This project capitalizes on my love for business administration! As a Business & Computer Science student at UBC, I 
wanted to incorporate the other half of my studies at Sauder into this project. Although accounting is one of the
most popular specializations in business, it is also indisputably one of the most tedious and repetitive. Accountants
and other financial managers often have very tedious expense management tasks that involve monitoring daily company
transactions. Any software that can make their tasks easier are high in demand, so I wanted to start 
with an application that is simple but in the field of financial management.

## User Stories

- As a user, I want to be able to add new students, teachers, and courses to the School
- As a user, I want to be able to remove students, teachers, and courses from the School
- As a user, I want to be able to enroll a student into a new course
- As a user, I want to be able to assign a teacher to teach a course
- As a user, I want to be able to increase the outstanding tuition fees due for the student
- As a user, I want to be able to increase the outstanding salaries due to teachers
- As a user, I want to be able to pay off the outstanding salaries to teachers
- As a user, I want to be able to write off the outstanding tuition from students when paid
- As a user, I want to be able to select a student and view their profile and tuition history
- As a user, I want to be able to select a teacher and view their profile and salary history
- As a user, I want to be able to see a financial overview of the School
- As a user, I want to be able to save the current state of the School
- As a user, I want to be able to reload the previous state of the School when I open the application again

## Instructions for Grader
- You can generate the first required event by adding Students to the School: Students -> First Name -> Last Name -> 
Add Student. The new student will then be displayed into the "Enrolled-Students" table.
- You can generate the second required event by adding Teachers to the School: Teachers -> First Name -> Last Name -> 
Add Teacher. The new teacher will then be displayed into the "Teachers" table.
- You can trigger the audio component of this application by clicking on any button! 
- You can automatically load the previous state of the application simply by opening it.
- You can automatically save the state of the application by closing it.

## Phase 4: Task 2
I have included a new type hierachy in my code. The new superclass is the abstract class SchoolMember, representing
all individuals who are members of the school. The two subclasses of SchoolMember are Student and Teacher, who
each have their own unique implementations of the abstract methods 'assignCourse' and 'payOutstandingTransaction'. This 
new SchoolMember class abstracts away the many similarities between a Student and a Teacher in a school, including 
having first and last names, unique IDs, courses enrolled in or taught, and having outstanding transactions due with 
the school.