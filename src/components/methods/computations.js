import { useCallback } from "react"
import { toast } from "react-toastify";
import { emailRegex, nameRegex, passwordRegex, phoneRegex, pinRegex } from "../variables/variables";

export const computeTotal=(cartdatas)=>{
   // console.log("'ttl mtt',totalAmount.toFixed(2)")
    let totalAmount = 0
    let val=cartdatas!=null?
    cartdatas.map((item)=>{
        totalAmount=totalAmount+(item.productFinalPrice*item.productQuantity)
    })
    :''
    return totalAmount.toFixed(2);
  
  }
  
export const computeItems=(cartdatas)=>{
    let total=0
    let val= cartdatas!=null >0 ? cartdatas.map((item)=>{
        total=total+item.productQuantity
    }):''
    return total;
    
  }
 
export const updatedPrice=(price,offer)=>{
  return price-(price*offer/100);
}

export const VALIDATE={
  NOT_EMPTY:"not_empty",
  EMAIL:"email",
  PIN:"pincode",
  PASSWORD:"password",
  PHONE:"phone",
  NAME:"name",

}
export const validateInput=(name,value)=>{
//  console.log(name, VALIDATE.PASSWORD,"validation called")
  switch(name){
      case VALIDATE.PIN:if(value!="" && pinRegex.test(value)){
                              return true;
                          }
                          else{
                              return false;
                          }
      case VALIDATE.PASSWORD:if(value!="" && passwordRegex.test(value)){
                                // console.log("paswword validation")
                                  return true;
                              }
                              else{
                               //   console.log("paswword validation")
                                  return false;
                              }
      case VALIDATE.PHONE:if(value!="" && phoneRegex.test(value)){
                              return true;
                          }
                          else{
                            //  console.log("phone validation")
                              return false;
                          }
      case VALIDATE.NAME:if(value!="" && nameRegex.test(value)){
                              return true;
                          }
                          else{
                           //  console.log("phone validation")
                              return false;
                          }
      case VALIDATE.EMAIL:if(value!="" && emailRegex.test(value)){
                              return true;
                          }
                          else{
                             // console.log("phone validation")
                              return false;
                          }
      default: return false;
      
  }
}

export const errorMessage=(message)=>{
      
  toast.error(message, {
      position: "top-right",
      autoClose: 1000,
      hideProgressBar: true,
      closeOnClick: true,
      pauseOnHover: true,
      draggable: true,
      progress: undefined,
      theme: "dark",
      });

}

export const successMessage=(message)=>{
  
  toast.success(message, {
      position: "top-right",
      autoClose: 1000,
      hideProgressBar: true,
      closeOnClick: true,
      pauseOnHover: true,
      draggable: true,
      progress: undefined,
      theme: "dark",
      });
}


