package pt.isec.pd.spring_boot.exemplo3.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.isec.pd.spring_boot.exemplo3.models.User;
import pt.isec.pd.spring_boot.manageDB.DbOperations;

@RestController
public class RegisterController {
    //private final DbOperations db;

    /*public RegisterController(DbOperations db){
        this.db = db;
    }*/
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        DbOperations db = DbOperations.getInstance();
        if(db.existUser(user.getEmail())) {
            return new ResponseEntity<>("This email is already registered", HttpStatus.BAD_REQUEST);
        }
        db.addUser(user.getEmail(),user.getPassword());
        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);

    }
}
