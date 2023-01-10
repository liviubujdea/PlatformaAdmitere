
package aplicatie_admitere.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;



@Entity
@Table
public class OptiuniCandidati {

    @Id
    @SequenceGenerator(
            name="OptiuniCandidati_sequence",
            sequenceName = "OptiuniCandidati_sequence",
            allocationSize = 1
    )

    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

  //  private Long candidat_id;

   // private Long specializare_id;

    @NotBlank
    private Integer prioritate;

    @ManyToOne
    @JoinColumn(name="candidat_id")
    private CandidatiInrolati candidatiInrolati;

    @ManyToOne
    @JoinColumn(name="specializari_id")
    private Specializari specializari;
    public OptiuniCandidati() {
    }

    public OptiuniCandidati(Long id, Long candidat_id, Long specializare_id, Integer prioritate) {
        this.id = id;
      //  this.candidat_id = candidat_id;
       // this.specializare_id = specializare_id;
        this.prioritate = prioritate;
    }

    public OptiuniCandidati(Long candidat_id, Long specializare_id, Integer prioritate) {
     //   this.candidat_id = candidat_id;
        //this.specializare_id = specializare_id;
        this.prioritate = prioritate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /*public Long getCandidat_id() {
        return candidat_id;
    }

    public void setCandidat_id(Long candidat_id) {
        this.candidat_id = candidat_id;
    }*/

    /*public Long getSpecializare_id() {
        return specializare_id;
    }

    public void setSpecializare_id(Long specializare_id) {
        this.specializare_id = specializare_id;
    }*/

    public Integer getPrioritate() {
        return prioritate;
    }

    public void setPrioritate(Integer prioritate) {
        this.prioritate = prioritate;
    }

    public CandidatiInrolati getCandidatiInrolati() {
        return candidatiInrolati;
    }

    public void setCandidatiInrolati(CandidatiInrolati candidatiInrolati) {
        this.candidatiInrolati = candidatiInrolati;
    }

    public Specializari getSpecializari() {
        return specializari;
    }

    public void setSpecializari(Specializari specializari) {
        this.specializari = specializari;
    }
}
