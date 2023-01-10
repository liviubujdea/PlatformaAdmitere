package aplicatie_admitere.controllers;

import aplicatie_admitere.models.CandidatiInrolati;
import aplicatie_admitere.models.Ierarhizare;
import aplicatie_admitere.models.MedieModel;
import aplicatie_admitere.models.User;
import aplicatie_admitere.repositories.CandidatiInrolatiRepository;
import aplicatie_admitere.repositories.UserRepository;
import aplicatie_admitere.servicies.CandidatService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class SecretarController {

    private final UserRepository userRepo;
    private final CandidatiInrolatiRepository candidatRepo;

    private final CandidatService candidatService;
    public SecretarController(UserRepository userRepo, CandidatiInrolatiRepository candidatRepo, CandidatService candidatService) {

        this.userRepo = userRepo;
        this.candidatRepo=candidatRepo;
        this.candidatService = candidatService;
    }

    @PostMapping("/adauga-nota")
    @CrossOrigin("*")
    public ResponseEntity<String> adaugaNota(@RequestBody MedieModel json)
    {   //gasim userul de tip candidat dupa cnp
        Optional<User> user=userRepo.findByCnp(json.getCnp());
        if(user.isPresent())
        {
            Optional<CandidatiInrolati> candidat=candidatRepo.findByUsers(user.get());
            if(candidat.isPresent())
            {
                Ierarhizare ierarhizare=new Ierarhizare();
                ierarhizare.setNota(json.getNota());
                ierarhizare.setCandidatiInrolati(candidat.get());
                System.out.println("A fost adaugat nota candidatului"+candidat.get().getId());
            }
            else
            {
                System.out.println("Utilizatorul cu acest cnp nu e candidat!");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        }
        else
        {
            System.out.println("Nu exista cnp-ul in baza de date");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

    }

    @PutMapping("/candidati-inrolati/{email}")
    @CrossOrigin("*")
    public ResponseEntity<Void> updateValidare(@PathVariable String email, @RequestBody CandidatiInrolati candidatInrolat) {
        candidatService.updateValidare(email, candidatInrolat.getValidare());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
