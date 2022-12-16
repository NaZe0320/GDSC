package com.gdsc.a7minutesworkout

enum class BMI(val Label: String, val Description: String) {
    LOW("저체중", "Oops! You really need to take better care of yourself! Eat more!"),
    NORMAL("정상","Congratulations! You are in a good shape!"),
    OVERWEIGHT("과체중","Oops! You really need to take care of your yourself! Workout maybe!"),
    OBESITY("비만","Oops! You really need to take care of your yourself! Workout maybe!"),
    SEVERE_OBESITY("고도비만","OMG! You are in a very dangerous condition! Act now!")


}