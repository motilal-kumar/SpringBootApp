package com.neosoft.service;


import com.neosoft.collection.User;
import com.neosoft.exception.UserCollectionException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.ConstraintViolationException;
import java.util.List;

public interface IUserService {
  public   String saveUser(User user) throws ConstraintViolationException, UserCollectionException;

  public List<User> getUserByUserNameOrSurname(String username, String surname);

 public List<User> getUserStartsWith(String name);

 public List<User> findAllUser();

 public void deleteUser(String user_id);

  //public User updateUser(String user_id, User user);

    public User updateUser(User user);

  public Page<User> searchUser(String username, String pincode, Pageable pagable);

 // User  softDelete(User id);
 public void softDelete(String id);

 public  List<User>  findByNameAndPinCode(String username, String pincode);

}
