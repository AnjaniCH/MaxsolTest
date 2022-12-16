package tes.maxsol.request;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SupplierRequest {
    private String supplier;
    private List<String> produk;
}
