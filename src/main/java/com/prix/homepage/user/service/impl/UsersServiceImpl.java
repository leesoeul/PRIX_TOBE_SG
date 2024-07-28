package com.prix.homepage.user.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prix.homepage.user.dao.UsersMapper;
import com.prix.homepage.user.pojo.Users;
import com.prix.homepage.user.service.UsersService;
import lombok.AllArgsConstructor;


@AllArgsConstructor
@Service
public class UsersServiceImpl implements UsersService{
    private final UsersMapper usersMapper;

    @Override
    public List<Users> getAllUsers(){
        List<Users> listUsersLogs = usersMapper.findAll();
        List<Users> listUsersLog = new ArrayList<>();
        for(Users UsersLog : listUsersLogs){
            listUsersLog.add(
                UsersLog.builder()
                    .id(UsersLog.getId())
                    .name(UsersLog.getName())
                    .email(UsersLog.getEmail())
                    .affiliation(UsersLog.getAffiliation())
                    .level(UsersLog.getLevel())
                    .build()
            );
        }
        return listUsersLog;
    }

    @Override
    @Transactional
    public void deleteAccount(Integer id) {
        usersMapper.deleteById(id);
    }
}
