package tes.maxsol.repository;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tes.maxsol.entity.Supplier;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    @Modifying
    @Query(value = "insert into supplier(nama_supplier) VALUES (:nama_supplier)", nativeQuery = true)
    @Transactional
    void insertSupplier(@Param("nama_supplier") String nama_supplier);
    
    @Modifying
    @Query(value = "update supplier set nama_supplier=:nama_supplier where id_supplier=:id_supplier", nativeQuery = true)
    @Transactional
    Integer updateSupplier(@Param("nama_supplier") String nama_supplier, @Param("id_supplier") Long id_supplier);
    
    @Query(value = "select * from supplier where nama_supplier like :nama_supplier ", nativeQuery = true)
    List<Supplier> filterSupplier(@Param("nama_supplier") String nama_supplier);
    
    @Query(value = "select id_supplier from supplier where nama_supplier = :nama_supplier ", nativeQuery = true)
    List<Long> findIdSupplier(@Param("nama_supplier") String nama_supplier);
    
    @Query(value = "select id_supplier from supplier where id_supplier = :id_supplier and nama_supplier = :nama_supplier ", nativeQuery = true)
    List<Long> checkSupplier(@Param("id_supplier") Long id_supplier, @Param("nama_supplier") String nama_supplier);
    
    @Modifying
    @Query(value = "UPDATE customer " +
        "INNER JOIN alamat ON alamat.id_alamat = customer.alamat_id " +
        "SET customer.nama = :nama, " +
        "customer.alamat_id = :alamat_id " +
        "WHERE customer.id_customer = :id_customer", nativeQuery = true)
    @Transactional
    Integer updateCustomerAlamat(@Param("nama") String nama, @Param("alamat_id") Long alamat_id, @Param("id_customer") Long id_customer);

    @Query(value = "select s.nama_supplier from supplier s left join produk p on s.id_supplier = p.supplier_id where p.id_produk = :id_produk ", nativeQuery = true)
    String findNamaSupplier(@Param("id_produk") Long id_produk);
    
    @Query(value = "select * from supplier where id_supplier = :id_supplier", nativeQuery = true)
    List<Supplier> findByIds(@Param("id_supplier") Long id_supplier);
    
    @Query(value = "select nama_supplier from supplier where nama_supplier = :nama_supplier", nativeQuery = true)
    String cekSupplier(@Param("nama_supplier") String nama_supplier);
}