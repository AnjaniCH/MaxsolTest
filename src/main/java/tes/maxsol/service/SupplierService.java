package tes.maxsol.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tes.maxsol.repository.SupplierRepository;

@Service
public class SupplierService {
    
    @Autowired
    SupplierRepository supplierRepository;
    
    public Boolean compareIdSupplier(Long id_supplier, String nama_supplier) {
        try {
            List<Long> sup = supplierRepository.checkSupplier(id_supplier, nama_supplier);
            Long checkSup = null;
            for(Long val : sup){
                checkSup = val;
            }

            if (id_supplier.equals(checkSup)) {
                return true;
            } else  {
                return false;
            }
        } catch (Exception e) {
            System.out.println("Pesan Error : " + e);
            return false;
        }
    }
    
    public Boolean compareNamaSupplier(String nama_supplier) {
        try {
            String namSup= supplierRepository.cekSupplier(nama_supplier);
            if (nama_supplier.equals(namSup)) {
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
