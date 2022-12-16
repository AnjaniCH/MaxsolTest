package tes.maxsol.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "produk")
public class Produk {

    public Produk(String nama) {
        
        this.nama = nama;
    }

    public Produk() {
        
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_produk")
    private Long id_produk;

    @NotEmpty(message = "Nama tidak boleh kosong!!")
    @Size(min = 3, message = "Nama minimal 3 karakter")
    @Size(max = 255, message = "Nama maksimal 255 karakter")
    @Column(name = "nama")
    private String nama;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;
    
    @JsonIgnore
    @ManyToMany(mappedBy = "produkYangDibeli")
    private Set<Customer> customerSet = new HashSet<>();

    public Long getId_produk() {
        return id_produk;
    }

    public Long setId_produk(Long id_produk) {
        return this.id_produk = id_produk;
    }

    public String getNama() {
        return nama;
    }

    public String setNama(String nama) {
        return this.nama = nama;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

}

