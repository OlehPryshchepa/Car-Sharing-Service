<h1 align="center">
   🚘 CAR SHARING SERVICE (group project)🚘
</h1>

### 📄 Project Description

In this project, we aim to address the existing problems in the car-sharing service of your city. The current system relies on manual management, paper records, and lacks essential features such as real-time car availability tracking, electronic payments, and user accountability for returning cars on time. To solve these issues, we will introduce a modern and efficient system that automates processes, enables real-time tracking, and supports electronic payments for rental usage. This will bring significant improvements to the overall user experience and streamline the car-sharing service's operations.

The main task of the project is the implementation of an online car rental management system. This system will
streamline the
work of the service administrators and greatly enhance the user experience.

### 🛠 Functional (what the system should do)

- Web-based
- Manage car sharing inventory
- Manage car rentals
- Manage customers
- Display notifications
- Handle payments
- Telegram notification

### Architecture

- `config` - this package contains configuration files.
- `controller` - this package contains the controllers.
- `dto` - this package contains data transfer objects that are used to encapsulate and transfer data between the
  different layers of the application. These objects help to unify requests and responses in the controllers.
- `exception` - this package contains custom exceptions.
- `lib` - this package contains custom annotations for validation email and password.
- `model` - this package contains the model for the database. This model is used to represent data entities in the
  database and is used by the DAO to map database records to Java objects.
- `repository` - this package contains the data access layer (also known as the repository layer) that is responsible
  for accessing and manipulating data in the database.
- `security` - this package contains security settings.
- `service` - this package contains the services that call the repositories. These services are responsible for
  performing business logic and coordinating the interactions between the controllers and the DAO.
- `telegrambot` - this package contains Telegram bot settings.

### 🚀Features

- Authentication Controller
    - POST: `/register` - register a new user
    - POST: `/login` - Login to the system, receive a token

- Users Controller
    - PUT: `/users/{id}/role` - update user role
    - GET: `/users/me` - get my profile info
    - PUT: `/users/me` - update profile info

- Cars Controller
    - POST: `/cars` - add a new car
    - GET: `/cars` - get a list of cars
    - GET: `/cars/{id}` - get car's detailed information by id
    - PATCH: `/cars/{id}`- update car (also manage inventory)
    - DELETE: `/cars/{id}` - delete car ин шв

- Rentals Controller
    - POST: `/rentals` - add a new rental
    - GET: `/rentals` - get rentals by userId and
    - GET: `/rentals/{id}` - get rental by
    - POST: `/rentals/{id}/return` - is called to process a car return

- Payments Controller
    - GET:    `/payments`    - get user's payments
    - POST:    `/payments`    - create payment session
    - GET:    `/payments/success`    - check successful Stripe payments (Endpoint for stripe redirection)
    - GET:    `/payments/cancel`    - return payment paused message

### Telegram notifications

In this project, we notify clients and managers using a Telegram bot.
Notifications come when leases are created, leases are overdue, and payments are successful.

### 💻How to Run and Test project
- Clone the repo on GitHub
- You need to get a token to create a Telegram-bot `https://t.me/BotFather`
- Register on [Stripe](https://stripe.com/)
- Set your credentials in `application.properties`
- Build the project: mvn compile
- Run the application: npm start or yarn start.
  You can test the operation of the application using swagger using
  address `http://localhost:8080/swagger-ui/index.html#/`

### 🛠 Technologies 🛠
* Java `17`
* Apache Maven `3.10.1`
* Spring:
    * Boot `3.0.6`
    * Data Jpa `3.0.6`
    * Web Mvc `6.0.8`
* Liquibase-core `4.17.2`
* lombok `1.18.26`
* Swagger UI
* SpringDoc `2.1.0`
* Checkstyle Plugin `3.1.1`

### Conclusions

By implementing these features in an online car rental management system, the service can automate processes,
provide real-time information, and offer a more convenient and efficient experience for both users and administrators.

### Our team Contacts
If you have any questions or suggestions, please feel free to contact us:

* Serhii Mykhliuk(mentor): [GitHub](https://github.com/maxline) |  [LinkedIn](https://www.linkedin.com/in/seleon000/)
* Oleg Prishepa(TeamLead) : [GitHub](https://github.com/OlehPryshchepa) |  [LinkedIn](https://www.linkedin.com/in/oleh-pryshchepa-a7bb1927a/)
* Pavlo Krasota(TeamLead assistant) : [GitHub](https://github.com/Pkrasota) |  [LinkedIn](https://www.linkedin.com/in/pavlo-krasota-094b2027a/)
* Khrystyna Nyrka(Default hard worker): [GitHub](https://github.com/khrystyna-dev) |  [LinkedIn](https://www.linkedin.com/in/khrystyna-nyrka-b6ab45194/)
* Maryna Hryshchenko(Default hard worker): [GitHub](https://github.com/marimarig) |  [LinkedIn](https://www.linkedin.com/in/maryna-hryshchenko-8b6914283/)
* Mykyta Naumov(Default hard worker): [GitHub](https://github.com/MykNich) |  [LinkedIn](-)
* Yevhen Tolkunov(Default hard worker): [GitHub](https://github.com/eugenetolkunov) |  [LinkedIn](https://www.linkedin.com/in/yevhen-tolkunov/)
* Vladyslav Kryvets(Default hard worker): [GitHub](https://github.com/vladyslavkryvets) |  [LinkedIn](https://www.linkedin.com/in/vladyslav-kryvets/)
* Victor Ruban(Default hard worker): [GitHub](https://github.com/VRuban373) |  [LinkedIn](-)
