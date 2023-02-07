package com.bci.ejercicio.mapper;


import com.bci.ejercicio.entity.Phone;
import com.bci.ejercicio.entity.User;
import com.bci.ejercicio.model.PhoneDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PhoneMapper {

    public PhoneDto toDto(Phone phone) {

        PhoneDto phoneDto = new PhoneDto();

        phoneDto.setNumber(phone.getNumber());
        phoneDto.setCityCode(phone.getCityCode());
        phoneDto.setCountryCode(phone.getCountryCode());

        return phoneDto;
    }

    public Phone toEntity(PhoneDto phoneDto, User user) {

        Phone phone = new Phone();

        phone.setNumber(phoneDto.getNumber());
        phone.setCityCode(phoneDto.getCityCode());
        phone.setCountryCode(phoneDto.getCountryCode());
        phone.setUser(user);

        return phone;
    }

    public List<PhoneDto> toDtos(List<Phone> phoneList) {

        return phoneList.stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<Phone> toEntities(List<PhoneDto> phoneDtoList, User user) {

        return phoneDtoList.stream().map(x -> this.toEntity(x, user)).collect(Collectors.toList());
    }
}
