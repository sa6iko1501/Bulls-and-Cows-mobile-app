package com.example.kraviibikove1.Models;

import com.example.kraviibikove1.enums.Position;

import java.util.ArrayList;

public class Digit {
    private final int number;

    private final Position position;
    private final ArrayList<Integer> numbers;

    public Digit(int num, int position, ArrayList<Integer> numbers){
        this.number = num;
        this.numbers = numbers;
        switch(position){
            case 0:{
                this.position = Position.First;
                break;
            }
            case 1:{
                this.position = Position.Second;
                break;
            }
            case 2:{
                this.position = Position.Third;
                break;
            }
            case 3:{
                this.position = Position.Fourth;
                break;
            }
            default:{
                throw new IllegalArgumentException("Fuckery occurred");
            }
        }
    }

    public int cowOrBull(){
        switch(position){
            case First:{
                if(numbers.contains(number)){
                    if(numbers.get(0)==number){
                        return 2;
                    }
                    return 1;
                }
                return 0;
            }
            case Second:{
                if(numbers.contains(number)){
                    if(numbers.get(1)==number){
                        return 2;
                    }
                    return 1;
                }
                return 0;
            }
            case Third:{
                if(numbers.contains(number)){
                    if(numbers.get(2)==number){
                        return 2;
                    }
                    return 1;
                }
                return 0;
            }
            case Fourth:{
                if(numbers.contains(number)){
                    if(numbers.get(3)==number){
                        return 2;
                    }
                    return 1;
                }
                return 0;
            }
        }
        return 0;
    }

    public int getNumber(){
        return this.number;
    }
}
