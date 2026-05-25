

-- 1. Create table variable
DECLARE @details dbo.historiku_detajet_array;

-- 2. Insert sample rows
INSERT INTO @details (emri_sherbimit, emri_atributit, pershkrimi, pagesa)
VALUES 
('Haircut', 'Fade', 'Skin fade haircut', 10.00),
('Beard', 'Trim', 'Beard shaping and trim', 5.50),
('Beard', 'Trim', 'Beard shaping and trim', 5.50),
('Beard', 'Trim', 'Beard shaping and trim', 5.50),
('Beard', 'Trim', 'Beard shaping and trim', 5.50),
('Beard', 'Trim', 'Beard shaping and trim', 5.50),
('Beard', 'Trim', 'Beard shaping and trim', 5.50),
('Beard', 'Trim', 'Beard shaping and trim', 5.50),
('Beard', 'Trim', 'Beard shaping and trim', 5.50),
('Beard', 'Trim', 'Beard shaping and trim', 5.50),
('Beard', 'Trim', 'Beard shaping and trim', 5.50),
('Beard', 'Trim', 'Beard shaping and trim', 5.50),
('Beard', 'Trim', 'Beard shaping and trim', 5.50),
('Beard', 'Trim', 'Beard shaping and trim', 5.50),
('Beard', 'Trim', 'Beard shaping and trim', 5.50),
('Beard', 'Trim', 'Beard shaping and trim', 5.50),
('Beard', 'Trim', 'Beard shaping and trim', 5.50),
('Beard', 'Trim', 'Beard shaping and trim', 5.50),
('Beard', 'Trim', 'Beard shaping and trim', 5.50),
('Beard', 'Trim', 'Beard shaping and trim', 5.50),
('Beard', 'Trim', 'Beard shaping and trim', 5.50),
('Beard', 'Trim', 'Beard shaping and trim', 5.50),
('Beard', 'Trim', 'Beard shaping and trim', 5.50),
('Beard', 'Trim', 'Beard shaping and trim', 5.50),
('Beard', 'Trim', 'Beard shaping and trim', 5.50),
('Beard', 'Trim', 'Beard shaping and trim', 5.50),
('Beard', 'Trim', 'Beard shaping and trim', 5.50),
('Beard', 'Trim', 'Beard shaping and trim', 5.50),
('Beard', 'Trim', 'Beard shaping and trim', 5.50),
('Beard', 'Trim', 'Beard shaping and trim', 5.50),
('Beard', 'Trim', 'Beard shaping and trim', 5.50),
('Beard', 'Trim', 'Beard shaping and trim', 5.50),
('Beard', 'Trim', 'Beard shaping and trim', 5.50),
('Beard', 'Trim', 'Beard shaping and trim', 5.50),
('Beard', 'Trim', 'Beard shaping and trim', 5.50),
('Beard', 'Trim', 'Beard shaping and trim', 5.50),
('Wash', 'Standard', 'Hair wash service', 3.00);

-- 3. Execute stored procedure
EXEC dbo.register_history
    @ID = 2031,
    @id_employee = 1015,
    @historiku_detajet = @details;
    select * from historiku;