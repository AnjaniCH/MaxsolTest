package tes.maxsol.repository;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tes.maxsol.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Modifying
    @Query(value = "insert into customer(nama) VALUES (:nama)", nativeQuery = true)
    @Transactional
    void insertCustomer(@Param("nama") String nama);
    
    @Modifying
    @Query(value = "UPDATE customer " +
        "INNER JOIN alamat ON alamat.id_alamat = customer.alamat_id " +
        "SET customer.nama = :nama, " +
        "customer.alamat_id = :alamat_id " +
        "WHERE customer.id_customer = :id_customer", nativeQuery = true)
    @Transactional
    Integer updateCustomerAlamat(@Param("nama") String nama, @Param("alamat_id") Long alamat_id, @Param("id_customer") Long id_customer);
    
    @Query(value = "select * from customer where nama like :nama ", nativeQuery = true)
    List<Customer> filterCustomer(@Param("nama") String nama);
    
    @Query(value = "select * from customer where id_customer = :id_customer ", nativeQuery = true)
    List<Customer> findAllByCustomerId(@Param("id_customer") Long id_customer);
    
    @Modifying
    @Query(value="DELETE c FROM customer c LEFT JOIN alamat a ON c.alamat_id = a.id_alamat " +
        " WHERE c.id_customer = :id_customer", nativeQuery = true)
    @Transactional
    void DeleteCustomer(@Param("id_customer") Long id_customer);
    
    @Query(value = "select customer.nama from customer left join alamat on customer.alamat_id = alamat.id_alamat where alamat.id_alamat = :id_alamat ", nativeQuery = true)
    String getNama(@Param("id_alamat") Long id_alamat);
    
    @Query(value = "select * from customer where id_customer = :id_customer", nativeQuery = true)
    List<Customer> findByIds(@Param("id_customer") Long id_customer);
    
}