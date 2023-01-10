package aplicatie_admitere.servicies;


import aplicatie_admitere.models.CandidatiInrolati;
import aplicatie_admitere.models.User;
import aplicatie_admitere.repositories.CandidatiInrolatiRepository;
import aplicatie_admitere.repositories.SpecializariRepository;
import aplicatie_admitere.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CandidatService {

    private final SpecializariRepository specializariRepository;

    private final UserRepository userRepo;

    private final  CandidatiInrolatiRepository candidatInrolatRepo;
    @Autowired
    public CandidatService(SpecializariRepository specializariRepository, UserRepository userRepo, CandidatiInrolatiRepository candidatInrolatRepo) {
        this.specializariRepository = specializariRepository;
        this.userRepo = userRepo;
        this.candidatInrolatRepo = candidatInrolatRepo;
    }

    public List<Object[]> getSpecializari()
    {
        return specializariRepository.getAllDenumireAndNrLocuri();
    }

    public ResponseEntity<Object> updateValidare(String email, String validare) {
        Optional<User> user = userRepo.findByEmail(email);
        Optional<CandidatiInrolati> candidat = candidatInrolatRepo.findByUsers(user.get());

        if(candidat.isPresent())
        { //System.out.println("ceva");
           // CandidatiInrolati candidatiInrolati= new CandidatiInrolati();
            //candidatiInrolati.setValidare(validare.getValidare());
           // candidat
           // candidatInrolatRepo.save(validare);
            CandidatiInrolati candidatt = candidat.get();
            candidatt.setValidare(validare);
            candidatInrolatRepo.save(candidatt);
        }
        else
        {
            System.out.println("Utilizatorul nu a fost gasit!");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        if(validare.equals("VALIDAT"))
        {
            System.out.println("Candidatul a fost validat dupa verificarea documentelor!");
        }
        else
        {
            System.out.println("Candidatul nu a fost validat dupa verificarea documentelor!");
        }
       
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }




}
