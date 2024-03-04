// import { toast } from "react-toastify";
import axios from "axios";
export const request = axios.create({
    baseURL: "http://localhost:8080/woodland",
  });

export const token=localStorage.getItem(localStorage.getItem('phone'))

export const ACTION={
    FIRST_NAME:"firstname",
    LAST_NAME:"lastname",
    EMAIL:"email",
    GENDER:"gender",
    DOB:"dob",
    LAND:"landmark",
    STATE:"state",
    CITY:"city",
    PINCODE:"pincode",
    ADDLINE:"addressLine",
    ADDTYPE:"addTypeInput"
}



export const pinRegex = /[1-9][0-9]{5,5}/;
export const phoneRegex = /[1-9][0-9]{9}/;
export const passwordRegex =/^[a-zA-Z0-9!@#\\$%\\^\\&*\\)\\ (+=._-]+$/g
export const emailRegex =/^[a-zA-Z0-9]+@[a-zA-Z0-9]+\.[A-Za-z]+$/
export const nameRegex=/^[a-zA-Z]{1,}$/;

