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
import tes.maxsol.entity.Alamat;
import tes.maxsol.exception.ResourceNotFoundException;
import tes.maxsol.repository.AlamatRepository;
import tes.maxsol.repository.CustomerRepository;
import tes.maxsol.response.AlamatResponse;
import tes.maxsol.response.ResponseHandler;
import tes.maxsol.service.AlamatService;

@RestController
@RequestMapping("/maxsol")
public class AlamatController {

    @Autowired
    private AlamatRepository alamatRepository;

    @Autowired
    private CustomerRepository customerRepository;
    
    @Autowired
    private AlamatService alamatService;

    @GetMapping("/alamat")
    public ResponseEntity<?> getAllAlamat() {
        try {
            List<Alamat> list = alamatRepository.findAll();
            List<AlamatResponse> responseList = new ArrayList<>();
            list.forEach(e -> {
                AlamatResponse aResponse = new AlamatResponse();
                String nama = customerRepository.getNama(e.getId_alamat());
                aResponse.setId(e.getId_alamat());
                aResponse.setKota(e.getKota());
                aResponse.setCustomer(nama);
                responseList.add(aResponse);
            });
            return new ResponseEntity<List<AlamatResponse>>(responseList, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(false, HttpStatus.MULTI_STATUS, e.getMessage(), null);
        }
    }

    @GetMapping("/alamat/{id}")
    public ResponseEntity<?> getAlamatById(@PathVariable(value = "id") Long id_alamat) throws ResourceNotFoundException {
        try {
            List<Alamat> list = alamatRepository.findByIds(id_alamat);
            List<AlamatResponse> responseList = new ArrayList<>();
            list.forEach(e -> {
                AlamatResponse aResponse = new AlamatResponse();
                String nama = customerRepository.getNama(e.getId_alamat());
                aResponse.setId(e.getId_alamat());
                aResponse.setKota(e.getKota());
                aResponse.setCustomer(nama);
                responseList.add(aResponse);
            });
            return ResponseHandler.generateResponse(true, HttpStatus.OK, "List berhasil ditampilkan!", responseList);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(false, HttpStatus.MULTI_STATUS, e.getMessage(), null);
        }
    }

    @PostMapping("/alamat")
    public ResponseEntity<?> createAlamat(@Valid @RequestBody Alamat al) throws MethodArgumentNotValidException {
        try {
            String kota = al.getKota();
            
            String kota2 = alamatRepository.cekKota(kota);
            Boolean check = alamatService.compareKota(kota2);
            
            if(check == true){
                return ResponseHandler.generateResponse(false, HttpStatus.BAD_REQUEST, null, "Data alamat gagal ditambahkan!");
            }else {
                alamatRepository.insertAlamat(kota);
                return ResponseHandler.generateResponse(true, HttpStatus.OK, null, "Data alamat berhasil ditambahkan!");
            }
        } catch (Exception e) {
            return ResponseHandler.generateResponse(false, HttpStatus.MULTI_STATUS, e.getMessage(), null);
        }
    }

    @PutMapping("/alamat/{id}")
    public ResponseEntity<?> updateAlamat(@PathVariable(value = "id") Long id_alamat,
            @Valid @RequestBody Alamat alDetail) throws ResourceNotFoundException {
        try {
            String kota;
            Alamat al = alamatRepository.findById(id_alamat)
                    .orElseThrow(() -> new ResourceNotFoundException("Data tidak ditemukan untuk id alamat ini : " + id_alamat));
            kota = al.setKota(alDetail.getKota());
            alamatRepository.updateAlamat(kota, id_alamat);
            return ResponseHandler.generateResponse(true, HttpStatus.OK, null, "Data Alamat Berhasil Diupdate");
        } catch (Exception e) {
            return ResponseHandler.generateResponse(false, HttpStatus.MULTI_STATUS, e.getMessage(), null);
        }
    }

    @DeleteMapping("/alamat/{id}")
    public Map<String, Boolean> deleteCustomer(@PathVariable(value = "id") Long id_alamat)
            throws ResourceNotFoundException {
        Alamat al = alamatRepository.findById(id_alamat)
                .orElseThrow(() -> new ResourceNotFoundException("Data tidak ditemukan untuk id alamat ini : " + id_alamat));

        alamatRepository.delete(al);
        Map<String, Boolean> response = new HashMap<>();
        response.put("Alamat Berhasil Dihapus", Boolean.TRUE);
        return response;
    }
}
