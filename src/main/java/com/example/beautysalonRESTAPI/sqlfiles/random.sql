
-- Execute register_client_history @ID = 21, @SherbimiID = 4, @pagesa = 20.0, @qmimiBazik = 20.0, @kohezgjatja = '00:30:00';
-- EXECUTE clientHistory @ClientId = 16;

/*

CREATE PROCEDURE clientHistory 
  @ClientId BIGINT
   as 
   begin
          SELECT
        c.emri, c.mbiemri,
        e.emri, e.mbiemri,
        h.data_sherbimit,

        d.id_historikut_detajet,
        d.emri_sherbimit,
        d.pagesa

    FROM historiku h
    INNER JOIN Clients c ON c.ID = h.ID
    INNER JOIN Employees e ON e.ID = h.id_employee
    LEFT JOIN historiku_detajet d ON d.id_historikut = h.id_historikut

    WHERE h.ID = @ClientId;
    END;

*/

 -- Exec clientHistory @ClientId = 26;


 --SELECT * FROM historiku_detajet;

 GRANT SELECT, INSERT, UPDATE, DELETE ON availableSkill to beautysalon_user;

-- GRANT REFERENCES ON TYPE::dbo.detajet TO beautysalon_user;
--GRANT EXECUTE ON SCHEMA::dbo TO beautysalon_user;

--ALTER TABLE skills
--ADD CONSTRAINT unique_employee_service UNIQUE (id_employee, id_service);

--ALTER TABLE employeeAvailability add sherbimet_id int;
--alter table employeeAvailability add constraint fk_id_sherbimeve_ava foreign key (sherbimet_id) references sherbimet (ID);
--update employeeAvailability set sherbimet_id = 1019 where id_employee = 1015;
--delete from 
--select * from availabilityDetails;