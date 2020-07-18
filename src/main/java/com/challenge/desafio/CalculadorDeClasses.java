package com.challenge.desafio;


import java.lang.annotation.Annotation;

import java.lang.reflect.Field;

import java.math.BigDecimal;

import java.util.Arrays;


import com.challenge.annotation.Somar;

import com.challenge.annotation.Subtrair;

import com.challenge.interfaces.Calculavel;


public class CalculadorDeClasses implements Calculavel {


    @Override

    public BigDecimal somar(Object obj)  {

        return calc(obj, Somar.class);

    }


    @Override

    public BigDecimal subtrair(Object obj) {  return calc(obj, Subtrair.class);  }


    @Override

    public BigDecimal totalizar(Object obj){

        return calc(obj,Somar.class).subtract(calc(obj,Subtrair.class));

    }


    public BigDecimal calc(Object obj, Class<? extends Annotation> annClass) {

        return Arrays.stream(obj.getClass().getDeclaredFields())

                .filter(f -> f.isAnnotationPresent(annClass))

                .map( f -> getValueField(f,obj))

                .reduce(BigDecimal.ZERO,BigDecimal::add);

    }


    public BigDecimal getValueField(Field f, Object obj) {

        try {

            f.setAccessible(true);

            return (BigDecimal) f.get(obj);

        }catch (IllegalAccessException err) {

            err.printStackTrace();

        }

        return BigDecimal.ZERO;


    }

}

