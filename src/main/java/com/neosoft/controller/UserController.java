package com.neosoft.controller;

import com.neosoft.collection.User;
import com.neosoft.exception.UserCollectionException;
import com.neosoft.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;

    @PostMapping("/save")
    public ResponseEntity<?> saveUser(@Valid @RequestBody User user) {

        System.out.println("user:" + user);

        try {
            userService.saveUser(user);
            return new ResponseEntity<User>(user, HttpStatus.OK);
        } catch (ConstraintViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (UserCollectionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/users")
    public ResponseEntity<?> getAllUser() {

        List<User> userList = userService.findAllUser();
        System.out.println("userList: " + userList);
        return new ResponseEntity<>(userList, userList.size() > 0 ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public List<User> getUserStartWith(@RequestParam("name") String name) {

        return userService.getUserStartsWith(name);
    }

    @GetMapping("/username/{username}/pincode/{pincode}")
    public ResponseEntity<?> findByUsenameAndPincode(@PathVariable String username, @PathVariable String pincode) {

        List<User> userList = null;
        userList = userService.findByNameAndPinCode(username, pincode);

        if (userList == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @DeleteMapping("/{user_id}")
    public String delete(@PathVariable("user_id") String id) {

        userService.deleteUser(id);

        return "User deleted successfully!";
    }

    @PutMapping("/update/{user_id}")
    public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable("user_id") String id) {

        user.setUser_id(id);
        return new ResponseEntity<>(userService.updateUser(user), HttpStatus.OK);
    }

    @GetMapping("/search")
    public Page<User>   searchUser(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String pincode,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer size

    ){
        Pageable pagable = PageRequest.of(page, size);
        return  userService.searchUser(username, pincode,pagable );

    }

    @GetMapping("/user/nameorsurname/")
    public ResponseEntity<List<User>> getUserByUserNameOrSurname(@RequestParam String username,
                                                                       @RequestParam String surname) {

        return new ResponseEntity<List<User>>(userService.getUserByUserNameOrSurname(username, surname), HttpStatus.OK);
    }

    @DeleteMapping("/softDelete/{user_id}")
    public String softDelete(@PathVariable("user_id") String id){

        userService.softDelete(id);

        return "User deleted successfully!";
    }





     /* @GetMapping("/users")
    public List<User> getAllUser (@RequestParam String field) {
        return userService.findAllUser(field);
    }*/


     /*@PutMapping("/update/{user_id}")
    public  User updateUser(@RequestBody User user, @PathVariable("user_id") String id){

        return userService.updateUser(id,user);
    }*/

    //@DeleteMapping("soft/{user_id}")
   /* @DeleteMapping("/softDelete/{user_id}")
    public String softDelete(@PathVariable("user_id") User user){

        userService.softDelete(user);

        return "User deleted successfully!";
    }*/

}
