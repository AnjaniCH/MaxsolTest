package tes.maxsol.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tes.maxsol.entity.Produk;
import tes.maxsol.entity.Supplier;
import tes.maxsol.exception.ResourceNotFoundException;
import tes.maxsol.repository.ProdukRepository;
import tes.maxsol.repository.SupplierRepository;
import tes.maxsol.request.SupplierRequest;
import tes.maxsol.response.ResponseHandler;
import tes.maxsol.response.SupplierResponse;
import tes.maxsol.service.SupplierService;

@RestController
@RequestMapping("/maxsol")
public class SupplierController {

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private ProdukRepository produkRepository;

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("/supplier")
    public ResponseEntity<?> getSuppliers() {
        try {
            List<Supplier> list = supplierRepository.findAll();
            List<SupplierResponse> responseList = new ArrayList<>();
            list.forEach(e -> {
                SupplierResponse sResponse = new SupplierResponse();
                sResponse.setId(e.getId_supplier());
                sResponse.setSupplier(e.getNama_supplier());
                List<String> produk = new ArrayList<>();
                for (Produk p : e.getProduk()) {
                    produk.add(p.getNama());
                }
                sResponse.setProduk(produk);
                responseList.add(sResponse);
            });
            return new ResponseEntity<List<SupplierResponse>>(responseList, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(false, HttpStatus.MULTI_STATUS, e.getMessage(), null);
        }
    }

    @GetMapping("/supplier/{id}")
    public ResponseEntity<?> getSupplierById(@PathVariable(value = "id") Long id_supplier) throws ResourceNotFoundException {
        try {
            Supplier angg = supplierRepository.findById(id_supplier)
                    .orElseThrow(() -> new ResourceNotFoundException("Data tidak ditemukan untuk id supplier ini : " + id_supplier));
            List<Supplier> list = supplierRepository.findByIds(id_supplier);
            List<SupplierResponse> responseList = new ArrayList<>();
            list.forEach(e -> {
                SupplierResponse sResponse = new SupplierResponse();
                sResponse.setId(e.getId_supplier());
                sResponse.setSupplier(e.getNama_supplier());
                List<String> produk = new ArrayList<>();
                for (Produk p : e.getProduk()) {
                    produk.add(p.getNama());
                }
                sResponse.setProduk(produk);
                responseList.add(sResponse);
            });
            return ResponseHandler.generateResponse(true, HttpStatus.OK, null, responseList);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(false, HttpStatus.MULTI_STATUS, e.getMessage(), null);
        }
    }

    @PostMapping("/supplier")
    public ResponseEntity<?> createSupplier(@Valid @RequestBody SupplierRequest sReq) {
        String nama = sReq.getSupplier();
        String nama2 = supplierRepository.cekSupplier(nama);

        Boolean check = supplierService.compareNamaSupplier(nama2);

        if (check == true) {
            return ResponseHandler.generateResponse(false, HttpStatus.BAD_REQUEST, null, "Data supplier gagal ditambahkan!");
        } else {
            Supplier sup = new Supplier(sReq);
            sup = supplierRepository.save(sup);
            for (String s : sReq.getProduk()) {
                Produk p = new Produk();
                p.setNama(s);
                p.setSupplier(sup);
                produkRepository.save(p);
            }
            return ResponseHandler.generateResponse(true, HttpStatus.OK, null, "Data supplier dan produk berhasil ditambahkan");
        }

    }

    @PutMapping("/supplier/{id}")
    public ResponseEntity<?> updateSupplier(@PathVariable(value = "id") Long id_supplier,
            @Valid @RequestBody Supplier supDetails) throws ResourceNotFoundException {
        try {

            Supplier angg = supplierRepository.findById(id_supplier)
                    .orElseThrow(() -> new ResourceNotFoundException("Data tidak ditemukan untuk id customer ini : " + id_supplier));

            angg.setNama_supplier(supDetails.getNama_supplier());
            supplierRepository.save(angg);
            return ResponseHandler.generateResponse(true, HttpStatus.OK, null, "Data customer berhasil diupdate");

        } catch (Exception e) {
            return ResponseHandler.generateResponse(false, HttpStatus.MULTI_STATUS, e.getMessage(), null);
        }
    }

    @DeleteMapping("/supplier/{id}")
    public Map<String, Boolean> deleteSupplier(@PathVariable(value = "id") Long id_supplier)
            throws ResourceNotFoundException {
        Supplier angg = supplierRepository.findById(id_supplier)
                .orElseThrow(() -> new ResourceNotFoundException("Data tidak ditemukan untuk id supplier ini : " + id_supplier));

        supplierRepository.delete(angg);
        Map<String, Boolean> response = new HashMap<>();
        response.put("Supplier Berhasil Dihapus", Boolean.TRUE);
        return response;
    }
}
