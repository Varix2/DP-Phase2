package pt.isec.pd.spring_boot.exemplo3.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pt.isec.pd.spring_boot.manageDB.DbOperations;
import pt.isec.pd.spring_boot.manageDB.data.User;

@RestController
public class RegisterController {
    //private final DbOperations db;

    /*public RegisterController(DbOperations db){
        this.db = db;
    }*/
    @GetMapping("/register")
    public String showRegistrationForm() {

        return "REGISTRATION";
    }
}
