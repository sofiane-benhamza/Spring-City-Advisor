package all.cities;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        // Customize the error message as needed
        String errorMessage = "<body style='background-color:black;min-width:100vw;min-height:100vh;margin:0;padding:0;'>"
                + "<h1 style='padding:50px;text-align:center;background-color:red;color:white'>🐋 Something went wrong ! 🐋 !</h1></body>";

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
    }
}
