import { useEffect, useState } from "react"
import { request } from "../variables/variables";


const useGetcategories=(type)=>{

    const [categories,setCategories] = useState([]);

    useEffect(()=>{
        //.log("inside effect")
        request
            .get(
                `/getCategories/${type}`,
                {
                    headers:{
                        "Content-Type":"application/json",
                        "Access-Control-Allow-Header":"Content-Type",
                        "Access-Control-Allow-Origin": "*",
                    }
    
                })
            .then((response)=>{
                    if(response.status===200){
                        //console.log("resp",response.data,'uel',type);
                        setCategories(response.data)
                    }
                })
            .catch(error=>{
                // setError(error)
                //console.log(error)
                setCategories(error)
            });
    },[type]);

    return [categories,setCategories];

}

export default useGetcategories