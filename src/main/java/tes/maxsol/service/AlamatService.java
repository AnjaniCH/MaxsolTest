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

@Service
public class AlamatService {
    
    @Autowired
    CustomerRepository customerRepository;
    
    @Autowired
    ProdukRepository produkRepository;
    
    @Autowired
    AlamatRepository alamatRepository;
    
    public Boolean compareKota(String kota) {
        try {
            String kot = alamatRepository.cekKota(kota);

            if (kota.equals(kot)) {
                return true;
            } else  {
                return false;
            }
        } catch (Exception e) {
            System.out.println("Pesan Error : " + e);
            return false;
        }
    }
   
}
