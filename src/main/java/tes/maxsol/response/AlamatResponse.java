/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tes.maxsol.response;

import java.util.List;
import lombok.Setter;
import lombok.Getter;
import tes.maxsol.entity.Customer;

@Setter
@Getter
public class AlamatResponse {
    private Long id;
    private String kota;
    private String customer;
}
