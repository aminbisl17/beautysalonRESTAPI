use beautysalon;

SELECT h.ID, h.id_historikut, h.Sherbimi, h.id_atributit, h.Data_sherbimit, 
               h.Pagesa, h.QmimiBazik, h.Zbritja, h.Pershkrimi, h.Kohezgjatja, 
               s.emri_sherbimit, a.opsioni 
               FROM beautysalon.historiku h 
               JOIN beautysalon.sherbimet s ON s.ID = h.Sherbimi 
               LEFT JOIN beautysalon.atributet_sherbimeve a ON a.id_atributit = h.id_atributit 
               WHERE h.ID =  1;