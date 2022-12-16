package tes.maxsol.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tes.maxsol.entity.Produk;
import tes.maxsol.exception.ResourceNotFoundException;
import tes.maxsol.repository.ProdukRepository;
import tes.maxsol.repository.SupplierRepository;
import tes.maxsol.response.ProdukResponse;
import tes.maxsol.response.ResponseHandler;
import tes.maxsol.service.ProdukService;

@RestController
@RequestMapping("/maxsol")
public class ProdukController {

    @Autowired
    private ProdukRepository produkRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private ProdukService produkService;

    @GetMapping("/produk")
    public ResponseEntity<?> getAllProduk() {
        try {
            List<Produk> list = produkRepository.findAll();
            List<ProdukResponse> responseList = new ArrayList<>();
            list.forEach(e -> {
                ProdukResponse pResponse = new ProdukResponse();
                String nama = supplierRepository.findNamaSupplier(e.getId_produk());
                pResponse.setId(e.getId_produk());
                pResponse.setProduk(e.getNama());
                pResponse.setSupplier(nama);
                responseList.add(pResponse);
            });
            return ResponseHandler.generateResponse(true, HttpStatus.OK, "List berhasil ditampilkan", responseList);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(false, HttpStatus.MULTI_STATUS, e.getMessage(), null);
        }
    }

    @GetMapping("/produk/{id}")
    public ResponseEntity<?> getProdukById(@PathVariable(value = "id") Long id_produk) throws ResourceNotFoundException {
        try {
            Produk angg = produkRepository.findById(id_produk)
                    .orElseThrow(() -> new ResourceNotFoundException("Data tidak ditemukan untuk id produk ini : " + id_produk));
            List<Produk> list = produkRepository.findByIds(id_produk);
            List<ProdukResponse> responseList = new ArrayList<>();
            list.forEach(e -> {
                ProdukResponse pResponse = new ProdukResponse();
                String nama = supplierRepository.findNamaSupplier(e.getId_produk());
                pResponse.setId(e.getId_produk());
                pResponse.setProduk(e.getNama());
                pResponse.setSupplier(nama);
                responseList.add(pResponse);
            });
            return ResponseHandler.generateResponse(true, HttpStatus.OK, null, responseList);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(false, HttpStatus.MULTI_STATUS, e.getMessage(), null);
        }
    }

    @PostMapping("/produk")
    public ResponseEntity<?> createProduk(@Valid @RequestBody Produk angg) throws MethodArgumentNotValidException {
        try {
            String nama = angg.getNama();

            String nama2 = produkRepository.cekNamaProduk(nama);
            Boolean check = produkService.compareNamaProduk(nama2);

            if (check == true) {
                return ResponseHandler.generateResponse(false, HttpStatus.BAD_REQUEST, null, "Data produk gagal ditambahkan!");
            } else {
                produkRepository.insertProduk(nama);
                return ResponseHandler.generateResponse(true, HttpStatus.OK, null, "Data produk berhasil ditambahkan!");
            }
        } catch (Exception e) {
            return ResponseHandler.generateResponse(false, HttpStatus.MULTI_STATUS, e.getMessage(), null);
        }
    }

    @PutMapping("/produk/{id}")
    public ResponseEntity<?> updateProduk(@PathVariable(value = "id") Long id_produk,
            @Valid @RequestBody Produk prodDetail) throws ResourceNotFoundException {
        try {
            String nama;
            Produk angg = produkRepository.findById(id_produk)
                    .orElseThrow(() -> new ResourceNotFoundException("Data tidak ditemukan untuk id produk ini : " + id_produk));

            nama = angg.setNama(prodDetail.getNama());
            Long id_produk2 = prodDetail.setId_produk(angg.getId_produk());
            produkRepository.updateProduk(nama, id_produk2);
            return ResponseHandler.generateResponse(true, HttpStatus.OK, null, "Data Produk Berhasil Diupdate");
        } catch (Exception e) {
            return ResponseHandler.generateResponse(false, HttpStatus.MULTI_STATUS, e.getMessage(), null);
        }
    }

    @DeleteMapping("/produk/{id}")
    public Map<String, Boolean> deleteProduk(@PathVariable(value = "id") Long id_produk)
            throws ResourceNotFoundException {
        Produk angg = produkRepository.findById(id_produk)
                .orElseThrow(() -> new ResourceNotFoundException("Data tidak ditemukan untuk id produk ini : " + id_produk));

        produkRepository.delete(angg);
        Map<String, Boolean> response = new HashMap<>();
        response.put("Produk Berhasil Dihapus", Boolean.TRUE);
        return response;
    }
}
