package aplicatie_admitere.repositories;

import aplicatie_admitere.models.CandidatiInrolati;
import aplicatie_admitere.models.Specializari;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpecializariRepository extends JpaRepository<Specializari,Long> {

    @Query("SELECT denumire, nr_locuri FROM Specializari")
    List<Object[]> getAllDenumireAndNrLocuri();


}
