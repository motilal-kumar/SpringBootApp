package com.neosoft.collection;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "user_col")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {

    @Id
    private String user_id;


    @NotNull(message = "Username is required.")
    @Size(min = 2, max = 100, message = "The length of username must be between 2 and 100 characters.")

    @Size(max = 50)
    private String username;

    private String surname;

    @NotNull(message = "age can not be null")
    private Integer age;

    @Size(max = 200)
    @NotNull(message = "hobbies can not be null")
    private List<String> hobbies;

    @NotNull(message = "dob can not be null")
    @Past(message = "The date of birth must be in the past.")
    private Date dob;

    @NotNull(message = "joining date can not be null")
    @Past(message = "The date of birth must be in the past.")
    private  Date joiningDate;

    @NotNull(message = "address can not be null")
    private List<Address> address;

    private boolean deleteFlag = Boolean.FALSE;



}
