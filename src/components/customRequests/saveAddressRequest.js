import { useEffect, useState } from "react"
import { request } from "../variables/variables";
// import { token } from "../variables/variables";

const useSaveaddressrequest=(add,flag,url)=>{

   const [response,setResponse] = useState(null);
   const token = localStorage.getItem(localStorage.getItem('phone'))

   useEffect(()=>{
     // console.log('saving the address now',add,flag)
      if(flag){
      //   console.log('saving the address now,,,,now->',add,token)
         request
         .post(url,add,
             {
                 headers:{
                     "Content-Type":"application/json",
                     "Access-Control-Allow-Header":"Content-Type",
                     "Access-Control-Allow-Origin": "*",
                     "Authorization" : `Bearer ${token}`
     
                 }
             })
         .then((response)=>{
          //   console.log("response data:",response.data)
             setResponse(response.data);
         }
         )
         .catch((error)=>{
         //  console.log('saving error...',error)
           setResponse(error)
         });
      }
   },[add,flag]);

   return [response,setResponse]
 
}

export default useSaveaddressrequest