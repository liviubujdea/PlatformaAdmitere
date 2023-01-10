package aplicatie_admitere.controllers;

import aplicatie_admitere.models.CandidatiInrolati;
import aplicatie_admitere.models.User;
import aplicatie_admitere.repositories.CandidatiInrolatiRepository;
import aplicatie_admitere.repositories.UserRepository;
import aplicatie_admitere.servicies.MinIOService;
import com.microsoft.sqlserver.jdbc.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@RestController
public class StorageController {
    private final UserRepository userRepo;
    private final CandidatiInrolatiRepository candidatInrolatRepo;

    public StorageController(UserRepository userRepo, CandidatiInrolatiRepository candidatInrolatRepo) {
        this.userRepo = userRepo;
        this.candidatInrolatRepo = candidatInrolatRepo;
    }

    @PostMapping("/add-files")
    @CrossOrigin("*")
    public ResponseEntity<Object> uploadpdf(
            @RequestParam("arhiva") MultipartFile arhiva,
            @RequestParam("email") String email) throws IOException, NoSuchAlgorithmException, InvalidKeyException {

        MinIOService minIOService = new MinIOService();
        String url = minIOService.WriteToMinIO(arhiva,email);
        email =  email.replace(("\""),"");

        Optional<User> user = userRepo.findByEmail(email);
        if (user.isPresent()) {
            long userId = user.get().getId();
            System.out.println(userId);
            Optional<CandidatiInrolati> candidatInrolat = candidatInrolatRepo.findByUsers(user.get());
            System.out.println(candidatInrolat.get().getId());
            candidatInrolat.get().setDocumente(url);
            candidatInrolatRepo.save(candidatInrolat.get());
            return ResponseEntity.status(HttpStatus.OK).body(url);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
}
