# Meddela
An android application for students to get college notices & updates using Google Firebase.

It will facilitate the students as well as the faculties of our institute to be able to get prompt updates and notifications regarding new notices and time tables /Schedule. The application will authenticate the users and then provide that user's corresponding information. The objective of our project is to facilitate the faculties and the students of our institute by providing instant updates through Real-Time Database.

---
**Functionality**

New users, who don’t have an account yet, can go to the account registration page and fill in their corresponding information to create a new account and log in. The students will fill in their corresponding branch, section and year at the time of registration, whereas the faculty members will have to provide their college provided Initials for database consistency.

If account registration is successful, the user’s unique ID is generated in the FirebaseAuth console. We use this UID as a node to store all details about the users in the Database. When the user reaches the App Dashboard, this UID is fetched from the real-time database which is then used to get details about the branch, section and year of the student or the initials in case if the user is a teacher. These details are used in all activities to get relevant information from the database and populate it onto the user’s screen.

The Time Table activity has a day-wise layout. When the activity is opened, the current day is selected automatically. There is a separate activity where the admin can add new time tables. At first, the branch, section and year of the class is selected, and then the admin fills in the details of all the lectures that need to be updated.

The app fetches the previously stored data from the database and shows it as hints in the edit text fields. As the user clicks on Update button after filling in the details, all the information is added to the Realtime database’s TimeTable node. Simultaneously, another Node is created by the Initials of the teacher and corresponding time table branches are created under that node.
The Initials that are provided by the Faculty at the time of registration are used as primary keys for the mapping of the time tables in the database. Any change in the Classes’ schedule is reflected teachers’ too.
In Account Activity, the UID is used to fetch user details from the database. The user can also update his/her details from the edit button.

Users can also upload notes by selecting the class and sections for whom the notes are intended. As the file is successfully uploaded on the Firebase Storage, its downloadable URL is captured. The download URL, file size, and date of upload and file name along with the designated class are stored in the database. When a user of that class and section logs in, he/she can view all these notes with corresponding details in the Notes Activity. Similarly, users can also send notices to students registered on the app. This feature is limited to users with Admin Privileges only. These notices can be viewed by users on the app Dashboard.

---
**Tech**

JDK The Time Tables are created as a Real-time database using firebase database. All users' information and notes are stored in Firebase Storage. Firebase Auth is used for providing logging in functionality. Firebase Cloud Messaging for snding notifications to users regarding any updates.

---
**App Requirements**

Minimum SDK Version 21
Android Version Android Lollipop or later