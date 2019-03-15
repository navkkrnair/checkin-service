package com.ams.checkin.component;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Component;

import com.ams.checkin.entity.CheckInRecord;
import com.ams.checkin.repository.CheckinRepository;

@Component
public class CheckinComponent
{
    private static final Logger logger = LoggerFactory.getLogger(CheckinComponent.class);

    CheckinRepository checkinRepository;
    CheckInSource     sender;

    @Autowired
    CheckinComponent(CheckinRepository checkinRepository, CheckInSource sender)
    {
        this.checkinRepository = checkinRepository;
        this.sender            = sender;
    }

    public long checkIn(CheckInRecord checkIn)
    {
        checkIn.setCheckInTime(new Date());
        logger.info("Saving checkin ");
        // save
        long          id = checkinRepository.save(checkIn)
                .getId();
        CheckInRecord cr = checkinRepository.findById(id)
                .get();
        logger.info("Successfully saved checkin ");
        // send a message back to booking to update status
        logger.info("Sending booking and checkin details \n Booking id" + cr.getBookingId()
                + "\n Checkin id " + id);
        CheckInMessage checkInDetails = new CheckInMessage();
        checkInDetails.setBookingId(cr.getBookingId());
        checkInDetails.setCheckInId(cr.getId());

        sender.checkInQ()
                .send(MessageBuilder.withPayload(checkInDetails)
                        .build());
        return id;

    }

    public CheckInRecord getCheckInRecord(long id)
    {
        return checkinRepository.findById(id)
                .get();
    }

}
