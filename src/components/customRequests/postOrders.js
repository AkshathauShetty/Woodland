import { useEffect, useState } from "react"
import { request } from "../variables/variables";
// import { token } from "../variables/variables";


const usePostorders=(url,finalOrder,saveOrder,setSaveOrders)=>{

    const [response,setResponse] = useState(null);
    const token = localStorage.getItem(localStorage.getItem('phone'))


    useEffect(()=>{
     //   console.log('saving the orders now',finalOrder,saveOrder)
        if(saveOrder){
        //    console.log('Inside the save orders call.',finalOrder,saveOrder,setSaveOrders)
           request
           .post(url,finalOrder,
               {
                   headers:{
                       "Content-Type":"application/json",
                       "Access-Control-Allow-Header":"Content-Type",
                       "Access-Control-Allow-Origin": "*",
                       "Authorization" : `Bearer ${token}`
       
                   }
               })
           .then((response)=>{
             //  console.log("response data:",response.data)
               setResponse(response.data);
            //    setSaveOrders(false)
           }
           )
           .catch((error)=>{
             console.log(error)
             setResponse(error)
           });
        }
  
     },[finalOrder,saveOrder]);

     return [response,setResponse]

}

export default usePostorders

// {
//     "orderId":0,
//     "orderTotalAmount":1230,
// 	"orderTotalItems":3,
//     "orderDonationAmount":30,
// 	"orderDeliveryAmount":0,
//     "addressId":1,
// 	"cartDto":	[{
//         "prodQu]antity":2,
//         "newPrice" :123.64,
//         "size":"M",
//         "prodPicId":{
//             "productPicId":10
//         }
//     }]
// }