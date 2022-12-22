package tes.maxsol.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import tes.maxsol.entity.Produk;

public interface ProdukRepository extends PagingAndSortingRepository<Produk, Long> {
    @Modifying
    @Query(value = "insert into produk(nama) VALUES (:nama)", nativeQuery = true)
    @Transactional
    void insertProduk(@Param("nama") String nama);
    
    @Modifying
    @Query(value = "update produk set nama=:nama where id_produk=:id_produk", nativeQuery = true)
    @Transactional
    void updateProduk(@Param("nama") String nama, @Param("id_produk") Long id_produk);
    
    @Query(value = "select * from produk where nama like :nama ", nativeQuery = true)
    List<Produk> filterProduk(@Param("nama") String nama);
    
    @Query(value = "select * from produk where id_produk = :id_produk ", nativeQuery = true)
    List<Produk> findAllByProdukId(@Param("id_produk") Long id_produk);
    
    @Modifying
    @Query(value = "UPDATE produk " +
        "SET nama = :nama " +
        "WHERE supplier_id = :supplier_id", nativeQuery = true)
    @Transactional
    Integer updateProdukSup(@Param("nama") List<String> nama, @Param("supplier_id") Long supplier_id);
    
    @Query(value = "select * from produk where id_produk = :id_produk", nativeQuery = true)
    List<Produk> findByIds(@Param("id_produk") Long id_produk);
    
    @Query(value = "select nama from produk where nama = :nama", nativeQuery = true)
    String cekNamaProduk(@Param("nama") String nama);
}