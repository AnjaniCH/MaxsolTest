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
public class CustomerService {
    
    @Autowired
    CustomerRepository customerRepository;
    
    @Autowired
    ProdukRepository produkRepository;
    
    @Autowired
    AlamatRepository alamatRepository;
    
    public List<Customer> getCustomerDetails(Long cusId){
        if(null != cusId){
            return customerRepository.findAllByCustomerId(cusId);
        } else {
            return customerRepository.findAll();
        }
    }

    public Customer produkYangDibeliCustomer(Long cusId, Long prodId) {
        Set<Produk> produkSet = null;
        Customer customer = customerRepository.findById(cusId).get();
        Produk produk = produkRepository.findById(prodId).get();
        produkSet = customer.getProdukYangDibeli();
        produkSet.add(produk);
        customer.setProdukYangDibeli(produkSet);
        return customerRepository.save(customer);
    }
    
    public Boolean compareIdAlamat(Long id_alamat, String kota) {
        try {
            List<Long> al = alamatRepository.checkAlamat(id_alamat, kota);
            Long checkAl = null;
            for(Long val : al){
                checkAl = val;
            }

            if (id_alamat.equals(checkAl)) {
                return true;
            } else  {
                return false;
            }
        } catch (Exception e) {
            System.out.println("Pesan Error : " + e);
            return false;
        }
    }
    
    public Boolean compareKota(String kota) {
        try {
            List<String> kot = alamatRepository.getKota2(kota);
            String kot2 = null;
            for(String val : kot){
                kot2 = val;
            }

            if (kota.equals(kot2)) {
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
