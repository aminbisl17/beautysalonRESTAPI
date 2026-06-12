-- Assuming your table is named 'sherbimet' (based on your API path '/api/mixed/sherbimet')
-- Please adjust table name and column names if they differ in your database schema

-- Adjusted to use integer values representing total minutes
INSERT INTO sherbimet (emri_sherbimit, pershkrimi, qmimi_baze, zbritja, kohezgjatja)
VALUES 
('Classic Gel Manicure', 'Full manicure with gel polish and cuticle care', 25.00, 10, 90),   -- 01:30:00 -> 90 minutes
('Luxury Pedicure', 'Relaxing foot soak, scrub, massage, and regular polish', 35.00, 0, 60),     -- 01:00:00 -> 60 minutes
('Balayage & Haircut', 'Custom hand-painted highlights, deep conditioning, and trim', 120.00, 15, 180), -- 03:00:00 -> 180 minutes
('HydraFacial Treatment', 'Deep cleansing, exfoliation, extraction, and hydration wrap', 75.00, 5, 45),   -- 00:45:00 -> 45 minutes
('Classic Lash Extensions', 'Full set of semi-permanent single-layer eyelash extensions', 60.00, 0, 120); -- 02:00:00 -> 120 minutes