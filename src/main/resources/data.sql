INSERT INTO USERS (NAME, EMAIL, PASSWORD, CALORIES_PER_DAY)
VALUES ('User', 'user@yandex.ru', '{noop}password', 2005),
       ('Admin', 'admin@gmail.com', '{noop}admin', 1900),
       ('Guest', 'guest@gmail.com', '{noop}guest', 2000);

INSERT INTO USER_ROLE (ROLE, USER_ID)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);

INSERT INTO MEAL (date_time, description, calories, user_id)
VALUES ('2020-01-30 10:00:00', 'Завтрак', 500, 1),
       ('2020-01-30 13:00:00', 'Обед', 1000, 1),
       ('2020-01-30 20:00:00', 'Ужин', 500, 1),
       ('2020-01-31 0:00:00', 'Еда на граничное значение', 100, 1),
       ('2020-01-31 10:00:00', 'Завтрак', 500, 1),
       ('2020-01-31 13:00:00', 'Обед', 1000, 1),
       ('2020-01-31 20:00:00', 'Ужин', 510, 1),
       ('2020-01-31 14:00:00', 'Админ ланч', 510, 2),
       ('2020-01-31 21:00:00', 'Админ ужин', 1500, 2);

INSERT INTO RESTAURANT (id, restaurant_name, description)
VALUES (1, 'Fast Food Rest', 'Fast Food Restauran: burgers, french fries, etc.'),
       (2, 'Itailian Causine Rest', 'Itailian Causine Restauant: pizza, etc'),
       (3, 'Vegan Rest', 'Vegan Restaurant: dishes without meat');

INSERT INTO DISH (dish_name, description, restaurant_id)
VALUES ('Big Burger', 'Plain Burger', 1),
       ('Special Burger', 'Very tasty burger', 1),
       ('French Fries', 'Plain French Fries', 1),
       ('Pizza Margherita', 'Itailian Pizza Margherita',2),
       ('Pizza quattro formaggi', 'Itailian Pizza quattro formaggi',2),
       ('Zucchini with paprika', 'Zucchini with paprika for vegans', 3),
       ('Vegan Lentil cutlets', 'Lentil protein is one of the highest quality and well—digested vegetable proteins', 3);

INSERT INTO VOTE (date_time, restaurant_id, user_id)
VALUES ('2020-01-30 10:00:00', 1, 1),
       ('2020-02-20 13:00:00', 1, 1),
       ('2020-03-30 20:00:00', 1, 1),
       ('2020-04-30 0:00:00', 1, 1),
       ('2020-05-31 10:00:00', 1, 1),
       ('2020-06-30 13:00:00', 1, 1),
       ('2020-07-31 20:00:00', 2, 1),
       ('2020-01-31 14:00:00', 1, 2),
       ('2020-02-20 21:00:00', 2, 2);