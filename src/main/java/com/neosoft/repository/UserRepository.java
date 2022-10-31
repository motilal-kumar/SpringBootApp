package com.neosoft.repository;

import com.neosoft.collection.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
   public  List<User>  findByusernameStartsWith(String user_name);

    List<User> findByUsernameOrSurname(String username, String surname);

    @Query("{'user': ?}")
   public Optional<User> findByUser(String user);

    //@Query(value = "UPDATE user SET deleteFlag = true where user_id=?1")

    @Query("update #{#user} u set u.deleteFlag = true where u.user_id=?1")
    public void softDeleteById(String user_id);

    //Soft delete.
    /*@Query("update #{#user} u set u.deleteFlag = true where u.id=?1")
    public void softDelete(String id);*/

    @Query(value = "{$and: [{'username':{$regex: '^?0'}}, {'address.pincode':{$regex: '^?1'}}]}")
    List<User> findByUsernameAndPincode(String username, String pincode);


}
