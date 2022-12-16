package tes.maxsol.repository;

import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tes.maxsol.entity.User;

public interface UserRepository extends JpaRepository<User, String> {
    @Query("Select nama from User WHERE nama = :nama")
    String findNama(@Param("nama") String nama);
    
    @Query("Select nama from User WHERE nama = :nama")
    String findNama2(@Param("nama") String nama);
    
    @Query("Select password from User WHERE nama = :nama")
    String findPassword(@Param("nama") String nama);
    
    @Query("Select role from User WHERE nama = :nama")
    String findRole(@Param("nama") String nama);
    
    @Modifying
    @Query(value = "update User set token = :token where nama = :nama", nativeQuery = true)
    @Transactional
    void updateToken(@Param("token") String token, @Param("nama") String nama);
}