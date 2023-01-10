package aplicatie_admitere.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;



@Entity
@Table
public class Ierarhizare {

    @Id
    @SequenceGenerator(
            name="ierarhizare_sequence",
            sequenceName = "ierarhizare_sequence",
            allocationSize = 1
    )

    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;



    @ManyToOne
    @JoinColumn(name="candidat_id")
    private CandidatiInrolati candidatiInrolati;



    @ManyToOne
    @JoinColumn(name="id_specializare")
    private Specializari specializare;
    @NotBlank
    private float nota;

    public Ierarhizare() {
    }

    public Ierarhizare(Long id, User candidat, Specializari specializare, float nota) {
        this.id = id;
       // this.candidat = candidat;
        this.specializare = specializare;
        this.nota = nota;
    }

    public Ierarhizare(User candidat, Specializari specializare, float nota) {
      //  this.candidat = candidat;
        this.specializare = specializare;
        this.nota = nota;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Specializari getSpecializare() {
        return specializare;
    }

    public void setSpecializare(Specializari specializare) {
        this.specializare = specializare;
    }

    public float getNota() {
        return nota;
    }

    public void setNota(float nota) {
        this.nota = nota;
    }

    public CandidatiInrolati getCandidatiInrolati() {
        return candidatiInrolati;
    }

    public void setCandidatiInrolati(CandidatiInrolati candidatiInrolati) {
        this.candidatiInrolati = candidatiInrolati;
    }
}
