-- Initial data for Library Management System

-- Insert admin user (password: admin123)
INSERT INTO users (email, password, first_name, last_name, userRole)
VALUES ('admin@library.com', '$2a$10$N.fKJPV.ZPGzYKqWYYZDz.Tg7gHvK/cOZKVlDN7eVQIQPV.WUXRbm', 'Admin', 'User', 'ADMIN');

-- Insert librarian user (password: lib123)
INSERT INTO users (email, password, first_name, last_name, userRole)
VALUES ('librarian@library.com', '$2a$10$HhTZpBHPCVcvHRqXTlbzx.Kv4eYQOjLVZm9ywb5WRRpImRCzRgn.a', 'John', 'Smith',
        'LIBRARIAN');

-- Insert reader users (password: reader123)
INSERT INTO users (email, password, first_name, last_name, userRole)
VALUES ('reader1@example.com', '$2a$10$q2Q4/MvP.yUzYzVGIBpXYORwEqQhFOmIwITrSBGOFVP1EHCnZDKwO', 'Alice', 'Johnson',
        'READER'),
       ('reader2@example.com', '$2a$10$q2Q4/MvP.yUzYzVGIBpXYORwEqQhFOmIwITrSBGOFVP1EHCnZDKwO', 'Bob', 'Williams',
        'READER');

-- Insert books
INSERT INTO books (title, author, isbn, genre, description, publication_year, total_copies, available_copies)
VALUES ('1984', 'George Orwell', '978-0452284234', 'Dystopian Fiction',
        'A dystopian novel set in a totalitarian society.', 1949, 3, 3),
       ('To Kill a Mockingbird', 'Harper Lee', '978-0061120084', 'Classic Fiction',
        'A classic of modern American literature.', 1960, 2, 2),
       ('The Great Gatsby', 'F. Scott Fitzgerald', '978-0743273565', 'Classic Fiction',
        'A novel about the American Dream.', 1925, 2, 2),
       ('Pride and Prejudice', 'Jane Austen', '978-0141439518', 'Romance', 'A romantic novel of manners.', 1813, 2, 2),
       ('The Catcher in the Rye', 'J.D. Salinger', '978-0316769174', 'Coming-of-age',
        'A story about teenage rebellion and angst.', 1951, 1, 1),
       ('Brave New World', 'Aldous Huxley', '978-0060850524', 'Science Fiction',
        'A dystopian novel about a technologically advanced future.', 1932, 2, 2),
       ('Animal Farm', 'George Orwell', '978-0452284241', 'Political Satire',
        'An allegorical novella about a group of farm animals.', 1945, 2, 2),
       ('The Lord of the Rings', 'J.R.R. Tolkien', '978-0544003415', 'Fantasy', 'An epic fantasy trilogy.', 1954, 3, 3),
       ('Harry Potter and the Philosopher''s Stone', 'J.K. Rowling', '978-0747532699', 'Fantasy',
        'The first book in the Harry Potter series.', 1997, 4, 4),
       ('The Hobbit', 'J.R.R. Tolkien', '978-0547928227', 'Fantasy', 'A fantasy novel and children''s book.', 1937, 2,
        2);

-- Insert book copies
INSERT INTO book_copies (book_id, inventory_number, status)
VALUES
-- 1984 copies
(1, 'INV-001-001', 'AVAILABLE'),
(1, 'INV-001-002', 'AVAILABLE'),
(1, 'INV-001-003', 'AVAILABLE'),
-- To Kill a Mockingbird copies
(2, 'INV-002-001', 'AVAILABLE'),
(2, 'INV-002-002', 'AVAILABLE'),
-- The Great Gatsby copies
(3, 'INV-003-001', 'AVAILABLE'),
(3, 'INV-003-002', 'AVAILABLE'),
-- Pride and Prejudice copies
(4, 'INV-004-001', 'AVAILABLE'),
(4, 'INV-004-002', 'AVAILABLE'),
-- The Catcher in the Rye copy
(5, 'INV-005-001', 'AVAILABLE'),
-- Brave New World copies
(6, 'INV-006-001', 'AVAILABLE'),
(6, 'INV-006-002', 'AVAILABLE'),
-- Animal Farm copies
(7, 'INV-007-001', 'AVAILABLE'),
(7, 'INV-007-002', 'AVAILABLE'),
-- The Lord of the Rings copies
(8, 'INV-008-001', 'AVAILABLE'),
(8, 'INV-008-002', 'AVAILABLE'),
(8, 'INV-008-003', 'AVAILABLE'),
-- Harry Potter copies
(9, 'INV-009-001', 'AVAILABLE'),
(9, 'INV-009-002', 'AVAILABLE'),
(9, 'INV-009-003', 'AVAILABLE'),
(9, 'INV-009-004', 'AVAILABLE'),
-- The Hobbit copies
(10, 'INV-010-001', 'AVAILABLE'),
(10, 'INV-010-002', 'AVAILABLE');

-- Insert sample orders
INSERT INTO book_orders (user_id, book_id, book_copy_id, order_type, status, issue_date, due_date, librarian_id)
VALUES (3, 1, 1, 'HOME', 'ISSUED', NOW(), DATE_ADD(NOW(), INTERVAL 14 DAY), 2),
       (4, 2, 4, 'READING_ROOM', 'ISSUED', NOW(), DATE_ADD(NOW(), INTERVAL 1 DAY), 2);

-- Update book copy status for issued books
UPDATE book_copies
SET status = 'ISSUED'
WHERE id IN (1, 4);

-- Update available copies count
UPDATE books
SET available_copies = available_copies - 1
WHERE id IN (1, 2);