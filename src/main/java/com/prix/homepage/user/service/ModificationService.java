package com.prix.homepage.user.service;

public interface ModificationService {

    void insertModification(String modName, String fullName, Integer classi, String md, String amd, String residue, String position);

    Integer selectMin();

    void updateMod(Integer minUserId);
}
