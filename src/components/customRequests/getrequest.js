import { useEffect, useState } from "react"
import { request } from "../variables/variables";


const useGetrequests=(url)=>{

    const [products,setProducts]=useState(); 
  //  const token =  localStorage.getItem(localStorage.getItem('phone'))

    useEffect(()=>{
      //  console.log("inside effect")
        request
            .get(
                `/${url}`,
                {
                    headers:{
                        "Content-Type":"application/json",
                        "Access-Control-Allow-Header":"Content-Type",
                        "Access-Control-Allow-Origin": "*",
                      
                    }
    
                })
            .then((response)=>{
                    if(response.status===200){
                       // console.log("resp",response.data,'uel',url);
                        setProducts(response.data)
                    }
                })
            .catch(error=>{
                // setError(error)
                //console.log(error)
            });
    },[url]);

    return [products,setProducts];
}

export default useGetrequests