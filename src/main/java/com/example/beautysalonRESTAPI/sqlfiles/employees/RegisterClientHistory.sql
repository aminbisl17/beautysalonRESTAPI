DECLARE @array AS dbo.historiku_detajet_array;

INSERT INTO @array (
    emri_sherbimit,
    emri_atributit,
    pershkrimi,
    pagesa
)
VALUES
(
    'Prerje Flokesh',
    'Premium',
    'Prerje moderne me stilim',
    15.00
),
(
    'Rruajtje',
    'Klasike',
    'Rruajtje tradicionale me brisk',
    10.00
),
(
    'Larje Flokesh',
    'Shampo Speciale',
    'Larje dhe trajtim i flokeve',
    8.50
);

EXEC register_history
    @ID = 21,
    @id_employee = 13,
    @historiku_detajet = @array;