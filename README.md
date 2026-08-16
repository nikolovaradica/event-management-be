# NexEvent Backend

Backend REST API for **NexEvent**, a web application for discovering and managing professional technology events.

The backend is implemented with **Spring Boot** and provides authentication, event management, categories, organizers, reviews, favorites, and Web Push notifications.

---

## Technologies

- Java 21
- Spring Boot 3.4.5
- Spring Web
- Spring Data JPA
- Spring Security
- PostgreSQL
- JWT authentication
- Maven
- Web Push / VAPID

---

## Features

### Authentication & Authorization

- User registration and login
- JWT-based authentication
- Role-based authorization
- `USER` and `ADMIN` roles

Regular users can:

- View events
- Manage their profile
- Add/remove favorites
- Add reviews
- Manage push notification subscriptions

Administrators can additionally:

- Create, update, and delete events
- Create, update, and delete categories
- Create, update, and delete organizers

---

### Events

The API supports:

- Creating and managing events
- Event details
- Searching events
- Filtering by category
- Filtering by city
- Filtering by date range
- Retrieving available event cities

Each event contains:

- Title
- Description
- Date and time
- City
- Venue
- Image
- Category
- Organizer
- Creation date

---

### Categories

Categories can be managed by administrators and are used for event classification and filtering.

---

### Organizers

Events are associated with organizers.

Each organizer contains:

- Name
- Website
- Description

Administrators can create, update, and delete organizers.

---

### Reviews

Authenticated users can review events.

Each review contains:

- Rating
- Comment
- Creation date
- User
- Event

Reviews are available on event details pages.

---

### Favorites

Authenticated users can add events to their favorites.

The API supports:

- Adding an event to favorites
- Removing an event from favorites
- Checking whether an event is favorited
- Retrieving a user's favorite events

Favorites are also used by the notification system to identify events for reminder notifications.

---

### Push Notifications

NexEvent supports Web Push notifications using VAPID.

Users can:

- Enable push notifications
- Disable push notifications
- Receive reminders for favorited events

The notification system sends reminders for events occurring the following day.

Notifications contain information about the event, including its:

- Title
- Time
- City
- Event ID

The event ID is included in the notification payload so that the frontend can open the corresponding event details page when the notification is clicked.

For demonstration purposes, the backend also provides a test endpoint for triggering the favorite-event reminder logic manually.

---

## Project Structure

The backend has the following package structure:

```text
src/main/java/mk/ukim/finki/event_platform_backend
│
├── auth/
│
├── event/
│   ├── model/
│   ├── repository/
│   ├── service/
│   └── web/
│
├── review/
│   ├── model/
│   ├── repository/
│   ├── service/
│   └── web/
│
├── user/
│   ├── model/
│   ├── repository/
│   ├── service/
│   └── web/
│
├── notification/
│   ├── config/
│   ├── model/
│   ├── repository/
│   ├── service/
│   └── web/
│
├── security/
│
└── common/
    ├── dto/
    └── exception/
```
The application is organized into functional modules rather than putting all controllers, services, and repositories into a single package.

## Database

NexEvent uses **PostgreSQL** as its relational database.

The main entities are:

```text
User
 ├── Favorite
 ├── Review
 └── PushSubscription

Event
 ├── Category
 ├── Organizer
 ├── Favorite
 └── Review
```

An `Event` belongs to one `Category` and one `Organizer`.

A `User` can:

- Favorite multiple events
- Review multiple events
- Have one or more push subscriptions

---

## Configuration

Create a `.env` file in the backend project root.

The following environment variables are required:

```env
# PostgreSQL
POSTGRES_DB=event_platform
POSTGRES_USER=your_username
POSTGRES_PASSWORD=your_password
POSTGRES_PORT=5432

# JWT
JWT_SECRET=your_jwt_secret
JWT_EXPIRATION=86400000

# Web Push / VAPID
VAPID_PUBLIC_KEY=your_vapid_public_key
VAPID_PRIVATE_KEY=your_vapid_private_key
VAPID_SUBJECT=mailto:your_email@example.com
```

The application reads these values through the corresponding Spring configuration:

```properties
spring.datasource.url=${POSTGRES_URL}
spring.datasource.username=${POSTGRES_USER}
spring.datasource.password=${POSTGRES_PASSWORD}

jwt.secret=${JWT_SECRET}
jwt.expiration=${JWT_EXPIRATION}

vapid.public-key=${VAPID_PUBLIC_KEY}
vapid.private-key=${VAPID_PRIVATE_KEY}
vapid.subject=${VAPID_SUBJECT}
```

### VAPID Keys

VAPID keys are used for Web Push authentication.

A new key pair can be generated using:

```bash
web-push generate-vapid-keys
```

The generated public key is also used by the frontend when creating the browser push subscription.

**Do not commit the `.env` file or private VAPID key to the repository.**

---

## Running the Backend

### Prerequisites

Make sure the following are installed:

- Java 21
- Maven
- PostgreSQL

### 1. Clone the repository

```bash
git clone https://github.com/nikolovaradica/event-management-be-monlith
cd event_platform_backend
```

### 2. Configure environment variables

Create the `.env` file and provide the required PostgreSQL, JWT, and VAPID values.

### 3. Start PostgreSQL

Create the database specified by `POSTGRES_DB`.

### 4. Run the application

Using Maven:

```bash
./mvnw spring-boot:run
```

On Windows:

```bash
mvnw.cmd spring-boot:run
```

Alternatively, the application can be started from an IDE of your choice.

The backend runs on:

```text
http://localhost:8080
```

---

## API

The API is organized around the following resources:

```text
/api/auth
/api/events
/api/categories
/api/organizers
/api/profile
/api/favorites
/api/notifications
```

### Authentication

```text
POST /api/auth/register
POST /api/auth/login
```

Authentication returns a JWT which must be included in authenticated requests:

```http
Authorization: Bearer <token>
```

---

### Events

```text
GET    /api/events
GET    /api/events/{id}
POST   /api/events
PUT    /api/events/{id}
DELETE /api/events/{id}
GET    /api/events/cities
```

Event creation, modification, and deletion require the `ADMIN` role.

---

### Categories

```text
GET    /api/categories
GET    /api/categories/{id}
POST   /api/categories
PUT    /api/categories/{id}
DELETE /api/categories/{id}
```

Write operations require the `ADMIN` role.

---

### Organizers

```text
GET    /api/organizers
GET    /api/organizers/{id}
POST   /api/organizers
PUT    /api/organizers/{id}
DELETE /api/organizers/{id}
```

Write operations require the `ADMIN` role.

---

### Reviews

Public event reviews can be retrieved with:

```text
GET /api/events/{id}/reviews
```

Authenticated users can create reviews:

```text
POST /api/events/{id}/reviews
```

---

### Favorites

Authenticated users can manage their favorite events through:

```text
/api/favorites
```

---

### Push Notifications

Subscribe the current user to push notifications:

```text
POST /api/notifications/subscriptions
```

Disable notifications:

```text
DELETE /api/notifications/subscriptions?endpoint=<subscription-endpoint>
```

Test the favorite-event reminder functionality:

```text
POST /api/notifications/test/favorite-event-reminder
```

The favorite-event reminder endpoint runs the reminder logic for events occurring the following day and sends notifications to users who have favorited those events.

---

## Security

The backend uses Spring Security with JWT authentication.

The JWT authentication filter:

1. Reads the `Authorization` header.
2. Extracts the JWT.
3. Validates the token.
4. Loads the corresponding user.
5. Places the authenticated user in the Spring Security context.
