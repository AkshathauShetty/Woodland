import { useEffect, useState } from "react"
import { request } from "../variables/variables";
import { useNavigate } from "react-router-dom";
import { errorMessage } from "../methods/computations";



// const token = localStorage.get('phone')
// export const authrequest = axios.create(
//     {
//         baseURL:"http://localhost:8080/woodland",
//         headers: {
//             "Authorization" : `Bearer ${token}`
//           }
       
//     }
// );



const useAuthGetrequests=(url,flag)=>{

    const [products,setProducts]=useState(); 
    const token =  localStorage.getItem(localStorage.getItem('phone'))
    const navigate = useNavigate();

    useEffect(()=>{
       // console.log("inside effect---token",token)
        request
            .get(
                `/${url}`,
                {
                    headers:{
                        "Content-Type":"application/json",
                        "Access-Control-Allow-Header":"Content-Type",
                        "Access-Control-Allow-Origin": "*",
                        "Authorization" : `Bearer ${token}`
                    }
    
                })
            .then((response)=>{
                    if(response.status===200){
                      //  console.log("resp",response.data);
                        setProducts(response.data)
                       // console.log("response done success");
                    }
                })
            .catch(error=>{
                if(error.response.status ==403){
                    localStorage.clear()
                    errorMessage('session expired login again to continue')
                    navigate('/home')
                }
                // console.log("error get auth",error,request);
                // errorMessage(error)
            });
    },[url,flag]);

    // const errorMessage=(message)=>{
        
    //     localStorage.clear();
    //     toast.error('Session expired! Please Login again!', {
    //         position: "top-right",
    //         autoClose: 3000,
    //         hideProgressBar: true,
    //         closeOnClick: true,
    //         pauseOnHover: true,
    //         draggable: true,
    //         progress: undefined,
    //         theme: "dark",
    //         });
    //     navigate('/home')
    // }
    return [products,setProducts];
}

export default useAuthGetrequests