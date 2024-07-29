package com.prix.homepage.user.service;

public interface ModificationUserService {

    void insertModification(String modName, String fullName, Integer classi, String md, String amd, String residue, String position);

    Integer selectMin();

    void updateMod(Integer minUserId);
}
