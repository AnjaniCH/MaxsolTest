package tes.maxsol.controller;

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
import tes.maxsol.entity.Alamat;
import tes.maxsol.entity.Customer;
import tes.maxsol.entity.Produk;
import tes.maxsol.exception.ResourceNotFoundException;
import tes.maxsol.repository.AlamatRepository;
import tes.maxsol.repository.CustomerRepository;
import tes.maxsol.request.CustomerAlamatRequest;
import tes.maxsol.response.CustomerResponse;
import tes.maxsol.response.ResponseHandler;
import tes.maxsol.service.CustomerService;

@RestController
@RequestMapping("/maxsol")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AlamatRepository alamatRepository;

    @Autowired
    private CustomerService customerService;

    @GetMapping("/customer")
    public ResponseEntity<?> getCustomer() {
        try {
            List<Customer> list = customerRepository.findAll();
            List<CustomerResponse> responseList = new ArrayList<>();
            list.forEach(e -> {
                CustomerResponse cResponse = new CustomerResponse();
                cResponse.setId(e.getId_customer());
                cResponse.setCustomer(e.getNama());
                List<String> kota = alamatRepository.getKota(e.getId_customer());
                String kota2 = null;
                for (String kot : kota) {
                    kota2 = kot;
                }
                cResponse.setKota(kota2);
                List<String> produk = new ArrayList<>();
                for (Produk p : e.getProdukYangDibeli()) {
                    produk.add(p.getNama());
                }
                cResponse.setProdukYangDibeli(produk);
                responseList.add(cResponse);
            });
            return ResponseHandler.generateResponse(true, HttpStatus.OK, "List berhasil ditampilkan!", responseList);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(false, HttpStatus.MULTI_STATUS, e.getMessage(), null);
        }
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable(value = "id") Long id_customer) throws ResourceNotFoundException {
        try {
            Customer angg = customerRepository.findById(id_customer)
                    .orElseThrow(() -> new ResourceNotFoundException("Data tidak ditemukan untuk id customer ini : " + id_customer));
            List<Customer> list = customerRepository.findByIds(id_customer);
            List<CustomerResponse> responseList = new ArrayList<>();
            list.forEach(e -> {
                CustomerResponse cResponse = new CustomerResponse();
                cResponse.setId(e.getId_customer());
                cResponse.setCustomer(e.getNama());
                List<String> kota = alamatRepository.getKota(e.getId_customer());
                String kota2 = null;
                for (String kot : kota) {
                    kota2 = kot;
                }
                cResponse.setKota(kota2);
                List<String> produk = new ArrayList<>();
                for (Produk p : e.getProdukYangDibeli()) {
                    produk.add(p.getNama());
                }
                cResponse.setProdukYangDibeli(produk);
                responseList.add(cResponse);
            });
            return ResponseHandler.generateResponse(true, HttpStatus.OK, null, responseList);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(false, HttpStatus.MULTI_STATUS, e.getMessage(), null);
        }
    }

    @PostMapping("/customer")
    public ResponseEntity<?> createCustomer(@Valid @RequestBody CustomerAlamatRequest eReq) {
        try {
            List<String> kota = alamatRepository.getKota2(eReq.getKota());
            String kota2 = null;
            for (String kot : kota) {
                kota2 = kot;
            }

            Boolean check = customerService.compareKota(kota2);

            if (check == true) {
                return ResponseHandler.generateResponse(false, HttpStatus.BAD_REQUEST, "Data customer gagal ditambahkan!", null);
            } else {
                Alamat al = new Alamat();
                al.setKota(eReq.getKota());
                al = alamatRepository.save(al);

                Customer cus = new Customer(eReq);
                cus.setAlamat(al);
                cus = customerRepository.save(cus);

                //return new ResponseEntity<Customer>(cus, HttpStatus.CREATED);
                return ResponseHandler.generateResponse(true, HttpStatus.OK, "Data customer berhasil ditambahkan", cus);
            }

        } catch (Exception e) {
            return ResponseHandler.generateResponse(false, HttpStatus.MULTI_STATUS, e.getMessage(), null);
        }
    }

    @PutMapping("/customer/{cusId}/produk/{prodId}")
    public ResponseEntity<?> produkYangDibeliCustomer(@PathVariable Long cusId, @PathVariable Long prodId) {
        try {
            customerService.produkYangDibeliCustomer(cusId, prodId);
            List<Customer> list = customerRepository.findAll();
            List<CustomerResponse> responseList = new ArrayList<>();
            list.forEach(e -> {
                CustomerResponse cResponse = new CustomerResponse();
                cResponse.setId(e.getId_customer());
                cResponse.setCustomer(e.getNama());
                List<String> kota = alamatRepository.getKota(e.getId_customer());
                String kota2 = null;
                for (String kot : kota) {
                    kota2 = kot;
                }
                cResponse.setKota(kota2);
                List<String> produk = new ArrayList<>();
                for (Produk p : e.getProdukYangDibeli()) {
                    produk.add(p.getNama());
                }
                cResponse.setProdukYangDibeli(produk);
                responseList.add(cResponse);
            });
            return ResponseHandler.generateResponse(true, HttpStatus.OK, "List berhasil ditampilkan!", responseList);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(false, HttpStatus.MULTI_STATUS, e.getMessage(), null);
        }
    }

    @PutMapping("/customer/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable(value = "id") Long id_customer,
            @Valid @RequestBody CustomerAlamatRequest cusDetail) throws ResourceNotFoundException {
        try {
            List<Long> id_alamat = alamatRepository.findIdAlamat(cusDetail.getKota());
            Long id_alamat2 = null;
            for (Long value : id_alamat) {
                id_alamat2 = value;
            }

            Boolean check = customerService.compareIdAlamat(id_alamat2, cusDetail.getKota());

            if (check == true) {
                Customer angg = customerRepository.findById(id_customer)
                        .orElseThrow(() -> new ResourceNotFoundException("Data tidak ditemukan untuk id customer ini : " + id_customer));

                String nama = angg.setNama(cusDetail.getNama());
                customerRepository.updateCustomerAlamat(nama, id_alamat2, id_customer);
                return ResponseHandler.generateResponse(true, HttpStatus.OK, null, "Data customer berhasil diupdate");
            } else {
                return ResponseHandler.generateResponse(false, HttpStatus.BAD_REQUEST, "Data customer gagal diupdate!", null);
            }
        } catch (Exception e) {
            return ResponseHandler.generateResponse(false, HttpStatus.MULTI_STATUS, e.getMessage(), null);
        }
    }

    @DeleteMapping("/customer/{id}")
    public Map<String, Boolean> deleteCustomer(@PathVariable(value = "id") Long id_customer)
            throws ResourceNotFoundException {
        Customer angg = customerRepository.findById(id_customer)
                .orElseThrow(() -> new ResourceNotFoundException("Data tidak ditemukan untuk id customer ini : " + id_customer));

        customerRepository.DeleteCustomer(angg.getId_customer());
        Map<String, Boolean> response = new HashMap<>();
        response.put("Customer Berhasil Dihapus", Boolean.TRUE);
        return response;
    }
}
