package tes.maxsol.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "alamat")
public class Alamat {

    public Alamat(String kota) {
        this.kota = kota;
    }

    public Alamat() {
        
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_alamat")
    private Long id_alamat;

    @NotEmpty(message = "Nama kota tidak boleh kosong!!")
    @Size(min = 3, message = "Nama kota minimal 3 karakter")
    @Size(max = 255, message = "Nama kota maksimal 255 karakter")
    @Column(name = "kota")
    private String kota;
    
    @OneToOne(mappedBy = "alamat")
    private Customer customer;

    public Long getId_alamat() {
        return id_alamat;
    }

    public Long setId_alamat(Long id_alamat) {
        return this.id_alamat = id_alamat;
    }

    public String getKota() {
        return kota;
    }

    public String setKota(String kota) {
        return this.kota = kota;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    
    
}
