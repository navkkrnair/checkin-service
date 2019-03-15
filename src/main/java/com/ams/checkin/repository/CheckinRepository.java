package com.ams.checkin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ams.checkin.entity.CheckInRecord;

public interface CheckinRepository extends JpaRepository<CheckInRecord, Long>
{

}
