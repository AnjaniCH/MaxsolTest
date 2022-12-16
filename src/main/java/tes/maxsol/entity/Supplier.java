package tes.maxsol.entity;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import tes.maxsol.request.SupplierRequest;

@Entity
@Table(name = "supplier")
public class Supplier {

    public Supplier(SupplierRequest sReq) {
        this.nama_supplier = sReq.getSupplier();
    }

    public Supplier() {
        
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_supplier")
    private Long id_supplier;

    @NotEmpty(message = "Nama Supplier tidak boleh kosong!!")
    @Size(min = 3, message = "Nama minimal 3 karakter")
    @Size(max = 255, message = "Nama maksimal 255 karakter")
    @Column(name = "nama_supplier")
    private String nama_supplier;
    
    @OneToMany(mappedBy="supplier")
    private List<Produk> produk;

    public Long getId_supplier() {
        return id_supplier;
    }

    public void setId_supplier(Long id_supplier) {
        this.id_supplier = id_supplier;
    }

    public String getNama_supplier() {
        return nama_supplier;
    }

    public String setNama_supplier(String nama_supplier) {
        return this.nama_supplier = nama_supplier;
    }

    public List<Produk> getProduk() {
        return produk;
    }

    public void setProduk(List<Produk> produk) {
        this.produk = produk;
    }
    
}

