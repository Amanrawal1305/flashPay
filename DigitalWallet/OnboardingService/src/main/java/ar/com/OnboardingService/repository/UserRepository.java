package ar.com.OnboardingService.repository;

import ar.com.OnboardingService.modal.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
     User findByMobileNo(String mobile);

}
