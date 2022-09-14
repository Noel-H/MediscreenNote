package com.noelh.mediscreennote.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "MediscreenPatient", url = "${MediscreenPatientUrl}")
public interface MediscreenPatientProxy {

    @GetMapping("check/{id}")
    Boolean isPatientIdExist(@PathVariable("id") Long id);
}
