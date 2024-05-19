package com.prix.homepage.download.repository;

import org.springframework.stereotype.Repository;
import com.prix.homepage.download.pojo.SoftwareRequest;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface RequestRepository extends JpaRepository<SoftwareRequest, Long> {

}
