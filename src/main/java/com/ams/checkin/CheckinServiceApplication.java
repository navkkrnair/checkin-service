package com.ams.checkin;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import com.ams.checkin.entity.CheckInRecord;
import com.ams.checkin.repository.CheckinRepository;

@SpringBootApplication
public class CheckinServiceApplication extends WebSecurityConfigurerAdapter
        implements CommandLineRunner
{
    private static final Logger logger = LoggerFactory.getLogger(CheckinServiceApplication.class);

    @Autowired
    CheckinRepository repository;

    public static void main(String[] args)
    {
        SpringApplication.run(CheckinServiceApplication.class,
                              args);
    }

    @Override
    public void run(String... strings) throws Exception
    {
        repository.deleteAll();
        CheckInRecord record = new CheckInRecord("Franc", "Gean", "28A", new Date(), "BF101", "22-JAN-16", 1);

        CheckInRecord result = repository.save(record);
        logger.info("checked in successfully ..." + result);

        logger.info("Looking to load checkedIn record...");
        logger.info("Result: " + repository.findById(result.getId())
                .get());

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http.authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.NEVER)
                .and()
                .httpBasic()
                .disable()
                .csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
    }

}
