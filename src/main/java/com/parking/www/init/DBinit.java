package com.parking.www.init;

import com.parking.www.model.RoleEnum;
import com.parking.www.model.entity.RoleEntity;
import com.parking.www.model.entity.UserEntity;
import com.parking.www.model.service.RoleRepository;
import com.parking.www.model.service.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class DBinit implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DBinit(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {

        initUserRoles();
        initAdmin();

    }


    private void initUserRoles() {
        List<RoleEntity> roleRepositoryList = roleRepository.findAll();
        if(roleRepositoryList.isEmpty()){

            for (RoleEnum value : RoleEnum.values()) {
                RoleEntity role = new RoleEntity();
                role.setRole(RoleEnum.valueOf(value.name()));
                roleRepository.save(role);
            }

        }
    }


    private void initAdmin() {
      final UserEntity admin = userRepository.findByUsername("admin");
      if(admin == null){
         List<RoleEntity> enumsList = roleRepository.findAll();
         Set<RoleEnum> setOfEnum =new HashSet<>();
          for (RoleEntity role : enumsList) {
              setOfEnum.add(role.getRole());
          }
          UserEntity newAdmin = new UserEntity();
          newAdmin
                  .setUsername("admin")
                  .setHashedPassword(passwordEncoder.encode("admin"))
                  .setProfilePicture("$2a$10$jpLNVNeA7Ar/ZQ2DKbKCm.MuT2ESe.Qop96jipKMq7RaUgCoQedV.'".getBytes())
                  .setRoleEnums(setOfEnum)
                  .setCreated(Instant.now())
                  .setModified(Instant.now());


          userRepository.save(newAdmin);
      }
    }

}
