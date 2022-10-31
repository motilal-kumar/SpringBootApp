package com.neosoft.service;

import com.neosoft.collection.User;
import com.neosoft.exception.ResourceNotFoundException;
import com.neosoft.exception.UserCollectionException;
import com.neosoft.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MongoTemplate  mongoTemplate;

    @Override
    public String saveUser(User user) throws ConstraintViolationException, UserCollectionException {

        Optional<User>  opt = userRepository.findByUser(user.getUsername());

        System.out.println("opt: "+opt);

        if(opt.isPresent()){
            throw new UserCollectionException(UserCollectionException.UserAlreadyExist());
        } else {
            userRepository.save(user).getUser_id();
        }

       // return userRepository.save(user).getUser_id();
        return "User saved successfully!";
    }

    @Override
    public List<User> getUserByUserNameOrSurname(String username, String surname) {

        List<User>  userList = userRepository.findByUsernameOrSurname(username, surname);
        System.out.println("userList: "+userList);

        if(userList.size()>0){

            return userList;

        }else{

            return new ArrayList<User>();
        }

    }

    @Override
    public List<User> getUserStartsWith(String name) {

        return userRepository.findByusernameStartsWith(name);
    }

    @Override
    public List<User> findAllUser() {

        List<User>  userList = userRepository.findAll(
                Sort.by("dob").descending()
                        .and(Sort.by("joiningDate"))
        );

        System.out.println("userList: "+userList);

        if(userList.size()>0){

            return userList;

        }else{

            return new ArrayList<User>();
        }

    }

    @Override
    public void deleteUser(String user_id) {
        Optional <User> userDb = this.userRepository.findById(user_id);

        System.out.println("userDb: "+userDb);

        if (userDb.isPresent()) {
            this.userRepository.deleteById(user_id);
        } else {
            throw new ResourceNotFoundException("Record not found with id : " +user_id);
        }

    }

    @Override
    public User updateUser(User user) {

        Optional<User>   findByUserId = userRepository.findById(user.getUser_id());

        System.out.println("findByUserId:"+findByUserId);

        if(findByUserId.isPresent()){

            User userEntity = findByUserId.get();

            userEntity.setUsername(user.getUsername());
            userEntity.setDob(user.getDob());
            userEntity.setAge(user.getAge());
            userEntity.setHobbies(user.getHobbies());
            userEntity.setAddress(user.getAddress());
            userEntity.setJoiningDate(user.getJoiningDate());

            userRepository.save(userEntity);

            return userEntity;

        } else {
            throw new ResourceNotFoundException("Record not found with id : " + user.getUser_id());
        }
    }

    @Override
    public Page<User> searchUser(String username, String pincode, Pageable pagable) {

        Query query = new Query().with(pagable);
        List<Criteria>  criteria = new ArrayList<>();

        if(username !=null && !username.isEmpty()){

            criteria.add(Criteria.where("firstname").regex(username,"i"));
        }

        if(pincode !=null){

            criteria.add(Criteria.where("address.pincode"));
        }

        if(!criteria.isEmpty()){

           query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[0])));
        }

        Page<User>  userPage = PageableExecutionUtils.getPage(
                mongoTemplate.find(query,User.class
                ),pagable, () -> mongoTemplate.count(query.skip(0).limit(0),User.class));


        return userPage;
    }

    @Override
    public void softDelete(String id) {

        userRepository.softDeleteById(id);

    }

    @Override
    public List<User> findByNameAndPinCode(String username, String pincode) {
        List<User>   userList =null;

        System.out.println("userList :"+userList);
        return  userList  = userRepository.findByUsernameAndPincode(username, pincode);
    }

     /* @Override
    public void delete(String id) {

        userRepository.deleteById(id);

    }*/

    /*   @Override
    public User updateUser(String user_id, User user) {

        Optional<User>   findByUserId = userRepository.findById(user_id);

        System.out.println("findByUserId:"+findByUserId);

        if(findByUserId.isPresent()){

            User userEntity = findByUserId.get();

                userEntity.setUsername(user.getUsername());
                userEntity.setDob(user.getDob());
                userEntity.setAge(user.getAge());
                userEntity.setHobbies(user.getHobbies());
                userEntity.setAddress(user.getAddress());
                userEntity.setJoiningDate(user.getJoiningDate());

                userRepository.save(userEntity);

        }
        return null;
    }*/

 /* @Override
    public User softDelete(User user) {
        User userEntity = new User();
        System.out.println("userEntity: "+userEntity);
        userEntity.setStatus("INACTIVE");
        userRepository.save(user);
        return user;
    }
*/

    /*  @Override
    public List<User> findAllUser(String field) {

        List<User>  userList = userRepository.findAll(Sort.by(Sort.Direction.ASC, field));

        System.out.println("userList: "+userList);

        if(userList.size()>0){

            return userList;

        }else{

            return new ArrayList<User>();
        }

    }*/


}
