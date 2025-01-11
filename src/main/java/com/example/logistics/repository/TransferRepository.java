package com.example.logistics.repository;

import com.example.logistics.model.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface TransferRepository extends JpaRepository<Transfer, Long> {

}
