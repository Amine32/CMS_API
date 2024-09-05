package com.example.demo.common;

import org.modelmapper.ModelMapper;

public class DtoConverter {

    private static final ModelMapper modelMapper = new ModelMapper();

    public static <S, T> T convert(S source, Class<T> targetClass) {
        return modelMapper.map(source, targetClass);
    }
}
