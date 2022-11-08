package ca.mcgill.ecse.mmss.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class MmssExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * @author Shidan Javaheri
     * This method handles all the times we throw an MmssException 
     * @param ex the thrown exception
     * @return a response entity that contains the error message and http status
     */
    @ExceptionHandler (MmssException.class)
    public ResponseEntity<String> handleMmssException (MmssException ex) { 
        return new ResponseEntity<String>(ex.getMessage(), ex.getStatus()); 
    }
}
