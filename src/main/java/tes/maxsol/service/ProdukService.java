package tes.maxsol.service;

import java.text.ParseException;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tes.maxsol.entity.Customer;
import tes.maxsol.entity.Produk;
import tes.maxsol.repository.AlamatRepository;
import tes.maxsol.repository.CustomerRepository;
import tes.maxsol.repository.ProdukRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class ProdukService {
    
    @Autowired
    CustomerRepository customerRepository;
    
    @Autowired
    ProdukRepository produkRepository;
    
    @Autowired
    AlamatRepository alamatRepository;
    
    
    public Boolean compareNamaProduk(String namaProd) {
        try {
            String prod = produkRepository.cekNamaProduk(namaProd);

            if (namaProd.equals(prod)) {
                return true;
            } else  {
                return false;
            }
        } catch (Exception e) {
            System.out.println("Pesan Error : " + e);
            return false;
        }
    }
    
    public Page<Produk> findAll2(Pageable pageable){
        return produkRepository.findAll(pageable);
    }
    
}
