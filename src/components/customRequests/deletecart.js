import { useEffect, useState } from "react"
import { request } from "../variables/variables";


const useDeleteCart=(deleteCart)=>{

    const [deletedCart,setDeletedCart] = useState(false)
    const token = localStorage.getItem(localStorage.getItem('phone'))

    useEffect(()=>{
        if(deleteCart){
            request
            .delete('/deleteAddedcart',
                {
                    
                    headers:{
                        "Content-Type":"application/json",
                        "Access-Control-Allow-Header":"Content-Type",
                        "Access-Control-Allow-Origin": "*",
                        "Authorization" : `Bearer ${token}`
                    }
                })
            .then((response)=>{
               // console.log("response data:",response.data,response.status)
                setDeletedCart(true)
                if(response.status==200){
                   // console.log('Radded cart deleted',response.data)
                    setDeletedCart(true)
                }        
            })
            .catch((error)=>{
               console.log("error----->",error);
                setDeletedCart(false);
            });
        }
     
    })

    return [deletedCart,setDeletedCart]
}

export default useDeleteCart