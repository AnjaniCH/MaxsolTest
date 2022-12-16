package tes.maxsol.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.ToString;
import tes.maxsol.request.CustomerAlamatRequest;

@ToString
@Entity
@Table(name = "customer")
public class Customer {
    
    public Customer() {
        
    }
    
    public Customer(CustomerAlamatRequest cReq){
        this.nama = cReq.getNama();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_customer")
    private Long id_customer;

    @NotEmpty(message = "Nama tidak boleh kosong!!")
    @Size(min = 3, message = "Nama minimal 3 karakter")
    @Size(max = 255, message = "Nama maksimal 255 karakter")
    @Column(name = "nama")
    private String nama;

    @JoinColumn( name = "alamat_id" )
    @OneToOne
    private Alamat alamat;
    
    @ManyToMany
    @JoinTable(name="customer_produk", 
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "produk_id"))
    private Set<Produk> produkYangDibeli = new HashSet<>();

    public Long getId_customer() {
        return id_customer;
    }

    public void setId_customer(Long id_customer) {
        this.id_customer = id_customer;
    }

    public String getNama() {
        return nama;
    }

    public String setNama(String nama) {
        return this.nama = nama;
    }

    public Alamat getAlamat() {
        return alamat;
    }

    public void setAlamat(Alamat alamat) {
        this.alamat = alamat;
    }

    public Set<Produk> getProdukYangDibeli() {
        return produkYangDibeli;
    }

    public void setProdukYangDibeli(Set<Produk> produkYangDibeli) {
        this.produkYangDibeli = produkYangDibeli;
    }
}

