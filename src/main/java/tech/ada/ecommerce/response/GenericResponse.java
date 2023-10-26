package tech.ada.ecommerce.response;

import lombok.Data;

@Data
public class GenericResponse {

    private int status;
    private Object data;
    private String message;

}
