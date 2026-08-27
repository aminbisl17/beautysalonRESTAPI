package com.example.beautysalonRESTAPI.dto.terminet;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.example.beautysalonRESTAPI.dto.Clients.ClientDTO;
import com.example.beautysalonRESTAPI.dto.Sherbimet.AtributetSherbimeveDTO;
import com.example.beautysalonRESTAPI.dto.Sherbimet.SherbimetAdminDTO;
import com.example.beautysalonRESTAPI.model.Detajet_termineve;
import com.example.beautysalonRESTAPI.model.Terminet;

public class TerminetGetDTO {
    
    private Long id_terminit;
    private ClientDTO client;
    private String pershkrimi;
    private LocalDateTime data_caktimit;
    private Long employee_id;
    private List<Detajet_Termineve> detajet_terminit;

    public TerminetGetDTO(){

    }

 public TerminetGetDTO(Terminet t) {

    this.id_terminit = t.getId_terminit();

    this.client = new ClientDTO(t.getClient());

    this.pershkrimi = t.getPershkrimi();

    this.data_caktimit = t.getData_caktimit();

    this.employee_id = t.getEmployee().getID();

Map<Long, List<Detajet_termineve>> grouped =
        t.getDetajet_termineve()
         .stream()
         .collect(Collectors.groupingBy(
                 (Detajet_termineve d) -> 
                     (Long) d.getSherbimet().getID(),
                 LinkedHashMap::new,
                 Collectors.toList()
         ));

    this.detajet_terminit = grouped.values()
            .stream()
            .map(details -> new Detajet_Termineve(details))
            .toList();
}

    public Long getId_terminit() {
        return id_terminit;
    }
    public void setId_terminit(Long id_terminit) {
        this.id_terminit = id_terminit;
    }
    public ClientDTO getClient() {
        return client;
    }
    public void setClient(ClientDTO client) {
        this.client = client;
    }
    public String getPershkrimi() {
        return pershkrimi;
    }
    public void setPershkrimi(String pershkrimi) {
        this.pershkrimi = pershkrimi;
    }
    public LocalDateTime getData_caktimit() {
        return data_caktimit;
    }
    public void setData_caktimit(LocalDateTime data_caktimit) {
        this.data_caktimit = data_caktimit;
    }
    public Long getEmployee_id() {
        return employee_id;
    }
    public void setEmployee_id(Long employee_id) {
        this.employee_id = employee_id;
    }

        public List<Detajet_Termineve> getDetajet_terminit() {
        return detajet_terminit;
    }

    public void setDetajet_terminit(List<Detajet_Termineve> detajet_terminit) {
        this.detajet_terminit = detajet_terminit;
    }

   public static class Detajet_Termineve {

    private Long id_terminit;
    private Long id_detajet_termineve;

    private SherbimetAdminDTO sherbimet;

    private List<AtributetSherbimeveDTO> atributet;

    private Double pagesa;
    private int kohezgjatja;


    public Detajet_Termineve(List<Detajet_termineve> details) {

    Detajet_termineve first = details.get(0);

    this.id_terminit = first.getTerminet().getId_terminit();

    this.id_detajet_termineve =
            first.getId_detajetTermineve();

    this.pagesa = first.getPagesa();

    this.kohezgjatja = first.getKohezgjatja();

    this.sherbimet =
            new SherbimetAdminDTO(first.getSherbimet());


    List<AtributetSherbimeveDTO> atributet =
            details.stream()
                    .map(d -> d.getAtributet_sherbimeve())
                    .filter(a -> a != null)
                    .map(a -> new AtributetSherbimeveDTO(a))
                    .toList();

    this.sherbimet.setAtributet(atributet);
}


    public List<AtributetSherbimeveDTO> getAtributet() {
        return atributet;
    }

    public void setAtributet(List<AtributetSherbimeveDTO> atributet) {
        this.atributet = atributet;
    }

    public Long getId_detajet_termineve() {
        return id_detajet_termineve;
    }

    public void setId_detajet_termineve(Long id_detajet_termineve) {
        this.id_detajet_termineve = id_detajet_termineve;
    }

    public Long getId_terminit() {
        return id_terminit;
    }

    public void setId_terminit(Long id_terminit) {
        this.id_terminit = id_terminit;
    }

    public SherbimetAdminDTO getSherbimet() {
        return sherbimet;
    }

    public void setSherbimet(SherbimetAdminDTO sherbimet) {
        this.sherbimet = sherbimet;
    }

    public Double getPagesa() {
        return pagesa;
    }

    public void setPagesa(Double pagesa) {
        this.pagesa = pagesa;
    }

    public int getKohezgjatja() {
        return kohezgjatja;
    }

    public void setKohezgjatja(int kohezgjatja) {
        this.kohezgjatja = kohezgjatja;
    }
}
}
