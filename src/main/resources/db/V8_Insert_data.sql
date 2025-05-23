
INSERT INTO roles (name, description) VALUES
('USER', 'Обычный пользователь'),
('ADMIN', 'Администратор системы');


INSERT INTO guitar_types (name, description) VALUES
('ACOUSTIC', 'Акустическая гитара'),
('ELECTRIC', 'Электрогитара'),
('BASS', 'Бас-гитара'),
('CLASSICAL', 'Классическая гитара');


INSERT INTO users (username, email, password, created_at, role_id) VALUES
('ivan', 'ivan@mail.ru', '$2a$10$N9qo8uLOickgx2ZMRZoMy.MH/r7r6Dt5Qn2Ue1lN6Lw7BQnkO3tK6', CURRENT_TIMESTAMP, 1),
('admin', 'admin@guitar.ru', '$2a$10$N9qo8uLOickgx2ZMRZoMy.MH/r7r6Dt5Qn2Ue1lN6Lw7BQnkO3tK6', CURRENT_TIMESTAMP, 2);


INSERT INTO guitars (id, name, description, image_url, created_at, added_by_id) VALUES
(1,'Fender Stratocaster', 'Легендарная электрогитара', 'https://www.thomann.de/blog/wp-content/uploads/2017/08/54-electric1.jpg', CURRENT_TIMESTAMP, 2),
(2,'Martin D-28', 'Акустическая гитара премиум-класса', 'https://images.deal.by/396345350_akusticheskaya-gitara-acoustic1.jpg', CURRENT_TIMESTAMP, 2);


INSERT INTO article_categories (name, description) VALUES
('THEORY', 'Теория музыки'),
('TUTORIALS', 'Обучающие материалы');


INSERT INTO article (title, content) VALUES
('Как играть аккорд Am', 'Подробное руководство для начинающих'),
('Выбор первой гитары', 'На что обратить внимание при покупке');


INSERT INTO chords (name, diagram_url, difficulty) VALUES
('Am', 'https://jguitar.com/images/chordshape/A-Minor-A-x%2C0%2C2%2C2%2C1%2C0.png', 'EASY'),
('C', 'https://jguitar.com/images/chordshape/C-Major-C-x%2C3%2C2%2C0%2C1%2C0.png', 'EASY'),
('F', 'https://jguitar.com/images/chordshape/F-Major-F-1%2C0%2C3%2C2%2C1%2Cx.png', 'MEDIUM');