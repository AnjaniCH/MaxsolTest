package tes.maxsol.repository;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tes.maxsol.entity.Alamat;

@Repository
public interface AlamatRepository extends JpaRepository<Alamat, Long> {
    @Modifying
    @Query(value = "insert into alamat(kota) VALUES (:kota)", nativeQuery = true)
    @Transactional
    void insertAlamat(@Param("kota") String kota);
    
    @Modifying
    @Query(value = "update alamat set kota=:kota where id_alamat=:id_alamat", nativeQuery = true)
    @Transactional
    void updateAlamat(@Param("kota") String kota, @Param("id_alamat") Long id_alamat);
    
    @Query(value = "select * from alamat where kota like :kota ", nativeQuery = true)
    List<Alamat> filterAlamat(@Param("kota") String kota);
    
    @Query(value = "select id_alamat from alamat where kota = :kota ", nativeQuery = true)
    List<Long> findIdAlamat(@Param("kota") String kota);
    
    @Query(value = "select id_alamat from alamat where id_alamat = :id_alamat and kota = :kota ", nativeQuery = true)
    List<Long> checkAlamat(@Param("id_alamat") Long id_alamat, @Param("kota") String kota);
    
    @Query(value = "select alamat.kota from alamat left join customer on alamat.id_alamat = customer.alamat_id where customer.id_customer = :id_customer", nativeQuery = true)
    List<String> getKota(@Param("id_customer") Long id_customer);
    
    @Query(value = "select kota from alamat where kota = :kota", nativeQuery = true)
    List<String> getKota2(@Param("kota") String kota);
    
    @Query(value = "select * from alamat where id_alamat = :id_alamat", nativeQuery = true)
    List<Alamat> findByIds(@Param("id_alamat") Long id_alamat);
    
    @Query(value = "select kota from alamat where kota = :kota", nativeQuery = true)
    String cekKota(@Param("kota") String kota);
}